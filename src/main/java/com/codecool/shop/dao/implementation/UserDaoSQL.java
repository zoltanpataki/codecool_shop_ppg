package com.codecool.shop.dao.implementation;

import com.codecool.shop.Config;
import com.codecool.shop.dao.UserDao;
import com.codecool.shop.model.User;

import javax.xml.transform.Result;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Arrays;
import java.util.Random;

public class UserDaoSQL implements UserDao {

    private static UserDaoSQL instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private UserDaoSQL() {
    }

    public static UserDaoSQL getInstance() {
        if (instance == null) {
            instance = new UserDaoSQL();
        }
        return instance;
    }

    private static String getSecurePassword(String passwordToHash, byte[] salt) {
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(salt);
            //Get the hash's bytes
            byte[] bytes = md.digest(passwordToHash.getBytes());
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    private static boolean executeQueryCheckUser(String query, String string) throws DaoConnectionException {
        boolean usernameExists = false;
        try {
            Connection connection = Config.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, string);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                usernameExists = true;
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.toString());
        }
        return usernameExists;
    }

    @Override
    public void addUser(User user) throws NoSuchProviderException, NoSuchAlgorithmException, DaoConnectionException {
        String query = "INSERT INTO users(username, password, email, salt ) VALUES (?, ?, ?, ?);";
        executeQuery(query, user);
    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public boolean checkUserExists(String userName) throws DaoConnectionException {
        String query = "SELECT username FROM users WHERE username = ? ;";
        return executeQueryCheckUser(query, userName);
    }

    @Override
    public boolean checkEmailExists(String email) throws DaoConnectionException {
        String query = "SELECT email FROM users WHERE email = ? ;";
        return executeQueryCheckUser(query, email);
    }

    private byte[] getSaltByUserName(String userName) throws DaoConnectionException {
        String query = "SELECT salt FROM users WHERE username = ? ;";
        return executeQueryGetSalt(query, userName);
    }

    public boolean isCredentialsValid(User user) throws DaoConnectionException {
        String query = "SELECT username, password FROM users WHERE username = ? AND password = ?;";
        System.out.println("username:" + user.getUserName() + " " + "password: " + user.getPassword());
        System.out.println("query visszatérési értéke: " + executeQueryCredentials(query, user));
        return executeQueryCredentials(query, user);
    }

    public int getId(String username) throws DaoConnectionException {
        String query = "SELECT id FROM users WHERE username = ?;";
        return executeQueryGetUserId(query, username);

    }

    public byte[] generateSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
        final Random r = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        r.nextBytes(salt);
        return salt;
    }

    public void executeQuery(String query, User user) throws NoSuchProviderException, NoSuchAlgorithmException, DaoConnectionException {
        byte[] salt = generateSalt();
        try (Connection connection = Config.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setString(1, user.getUserName());
            statement.setString(2, getSecurePassword(user.getPassword(), salt));
            statement.setString(3, user.getEmail());
            statement.setBytes(4, salt);

            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean executeQueryCredentials(String query, User user) throws DaoConnectionException {
        boolean isLoginValid = false;
        try {
            Connection connection = Config.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getUserName());
            statement.setString(2, getSecurePassword(user.getPassword(), getSaltByUserName(user.getUserName())));
//            System.out.println("salt: " + getSaltByUserName(user.getUserName()).getClass());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                isLoginValid = true;
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return isLoginValid;
    }

    private byte[] executeQueryGetSalt(String query, String username) throws DaoConnectionException {
        byte[] salt = new byte[16];
        try {
            Connection connection = Config.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = rs.getString(i);
                    System.out.print("Elvileg ez a salt: " + columnValue + " " + rsmd.getColumnName(i));

                }
                salt = rs.getBytes("salt");
                System.out.println("");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salt;
    }

    private int executeQueryGetUserId(String query, String username) throws DaoConnectionException {
        try {
            Connection connection = Config.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
