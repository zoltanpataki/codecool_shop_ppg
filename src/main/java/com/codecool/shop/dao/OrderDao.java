package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.DaoException;
import com.codecool.shop.model.Order;

import java.util.List;

public interface OrderDao {

    void add(Order order) throws DaoException;

    Order find(int id) throws DaoException;

    void remove(int id) throws DaoException;

    int generateNewOrderId() throws DaoException;

    void updateStatus(int orderId, String status) throws DaoException;

    List<Order> getAll() throws DaoException;
}

