package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.DaoConnectionException;
import com.codecool.shop.model.User;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public interface UserDao {

    void addUser(User user) throws NoSuchProviderException, NoSuchAlgorithmException, DaoConnectionException;
    void deleteUser(User user);
    void updateUser(User user);
    boolean checkUserExists(String userName) throws DaoConnectionException;
    boolean checkEmailExists(String email) throws DaoConnectionException;
    boolean isCredentialsValid(User user) throws NoSuchProviderException, NoSuchAlgorithmException, DaoConnectionException;
    int getId(String username) throws DaoConnectionException;
}
