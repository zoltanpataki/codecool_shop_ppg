package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.ProductCategoryDaoSql;

public class ProductCategoryDaoSqlTest extends ProductCategoryDaoTest<ProductCategoryDaoSql> {

    @Override
    protected ProductCategoryDaoSql createInstance() {
        return ProductCategoryDaoSql.getInstance();
    }
}
