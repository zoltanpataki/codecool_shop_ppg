package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.DaoException;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.util.List;

public interface ProductDao {

    void add(Product product) throws DaoException;

    Product find(int id) throws DaoException;

    void remove(int id) throws DaoException;

    List<Product> getAll() throws DaoException;

    List<Product> getBy(Supplier supplier) throws DaoException;

    List<Product> getBy(ProductCategory productCategory) throws DaoException;

}
