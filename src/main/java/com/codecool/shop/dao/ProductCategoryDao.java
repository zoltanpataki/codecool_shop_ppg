package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.DaoException;
import com.codecool.shop.model.ProductCategory;

import java.util.List;

public interface ProductCategoryDao {

    void add(ProductCategory category) throws DaoException;

    ProductCategory find(int id) throws DaoException;

    void remove(int id) throws DaoException;

    List<ProductCategory> getAll() throws DaoException;

}
