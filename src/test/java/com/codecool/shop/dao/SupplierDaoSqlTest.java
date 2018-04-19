package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.SupplierDaoSql;

public class SupplierDaoSqlTest extends SupplierDaoTest<SupplierDaoSql> {

    @Override
    protected SupplierDaoSql createInstance() {
        return SupplierDaoSql.getInstance();
    }

}
