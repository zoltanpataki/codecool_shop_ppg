package com.codecool.shop.dao.implementation;

public class DaoException extends Exception {

    // Parameterless Constructor
    public DaoException() {}

    // Constructor that accepts a message
    public DaoException(String message) {
        super(message);
    }
}
