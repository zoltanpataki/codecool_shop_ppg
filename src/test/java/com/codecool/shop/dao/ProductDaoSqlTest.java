package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.ProductDaoSql;

public class ProductDaoSqlTest extends ProductDaoTest<ProductDaoSql> {

    @Override
    protected ProductDaoSql createInstance() {
        return ProductDaoSql.getInstance();
    }
}
