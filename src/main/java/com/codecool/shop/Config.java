package com.codecool.shop;

import com.codecool.shop.dao.implementation.DaoConnectionException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Config {

    private static GetPropertyValues properties = new GetPropertyValues();
    private static final String DATABASE = "jdbc:postgresql://localhost:5432/" + readProperties("database");
    private static final String DATABASETEST = "jdbc:postgresql://localhost:5432/" + readProperties("databasetest");
    private static final String DB_USER = readProperties("user");
    private static final String DB_PASSWORD = readProperties("password");
    private static String environment = "prod";

    public static void setEnvironment(String environment) {
        Config.environment = environment;
    }

    public static String getEnvironment() {
        return environment;
    }

    private static String readProperties(String wishedInfo) {
        String result = "";
        try {
            String database = properties.getPropValues("database");
            String databaseTest = properties.getPropValues("databasetest");
            String user = properties.getPropValues("user");
            String password = properties.getPropValues("password");

            if (wishedInfo.equals("database")) {
                result = database;
            } else if (wishedInfo.equals("user")) {
                result = user;
            } else if (wishedInfo.equals("password")) {
                result = password;
            } else if (wishedInfo.equals("databasetest")){
                result = databaseTest;
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static Connection getConnection() throws DaoConnectionException {
        try {
            if (environment.equals("prod")) {
                return DriverManager.getConnection(
                        DATABASE,
                        DB_USER,
                        DB_PASSWORD);

            } else if (environment.equals("test")) {
                return DriverManager.getConnection(
                        DATABASETEST,
                        DB_USER,
                        DB_PASSWORD);
            }
            throw new DaoConnectionException("no database configured for the current environment");
        } catch (SQLException ex) {
            throw new DaoConnectionException(ex.getMessage());
        }
    }
}
