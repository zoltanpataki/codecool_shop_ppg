package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.DaoException;
import com.codecool.shop.model.Supplier;

import java.util.List;

public interface SupplierDao {

    void add(Supplier supplier) throws DaoException;

    Supplier find(int id) throws DaoException;

    void remove(int id) throws DaoException;

    List<Supplier> getAll() throws DaoException;
}
