package com.codecool.shop.dao.implementation;

public class DaoConnectionException extends DaoException {

    // Parameterless Constructor
    public DaoConnectionException() {}

    // Constructor that accepts a message
    public DaoConnectionException(String message) {
        super(message);
    }

}
