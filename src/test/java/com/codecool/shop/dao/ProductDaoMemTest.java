package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.ProductDaoMem;

public class ProductDaoMemTest extends ProductDaoTest<ProductDaoMem> {

    @Override
    protected ProductDaoMem createInstance() {
        return ProductDaoMem.getInstance();
    }
}
