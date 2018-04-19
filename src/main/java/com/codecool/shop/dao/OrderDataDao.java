package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.DaoException;
import com.codecool.shop.model.LineItem;

import java.sql.SQLException;
import java.util.List;

public interface OrderDataDao {

    void add(LineItem lineItem, int orderId) throws DaoException;

    LineItem find(int id) throws DaoException;

    void remove(int id) throws DaoException;

    int getPrice(int orderId) throws DaoException;

    List<LineItem> getAll() throws DaoException;
}
