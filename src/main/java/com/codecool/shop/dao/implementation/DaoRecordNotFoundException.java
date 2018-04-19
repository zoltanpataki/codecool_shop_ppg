package com.codecool.shop.dao.implementation;

public class DaoRecordNotFoundException extends DaoException {

    // Parameterless Constructor
    public DaoRecordNotFoundException() {}

    // Constructor that accepts a message
    public DaoRecordNotFoundException(String message) {
        super(message);
    }

}
