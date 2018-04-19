package com.codecool.shop.dao;

import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;

public class ProductCategoryDaoMemTest extends ProductCategoryDaoTest<ProductCategoryDaoMem> {

    @Override
    protected ProductCategoryDaoMem createInstance() {
        return ProductCategoryDaoMem.getInstance();
    }
}
