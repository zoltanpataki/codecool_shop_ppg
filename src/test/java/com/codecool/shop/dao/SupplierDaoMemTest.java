package com.codecool.shop.dao;
import com.codecool.shop.dao.implementation.SupplierDaoMem;

public class SupplierDaoMemTest extends SupplierDaoTest<SupplierDaoMem> {

    protected SupplierDaoMem createInstance() {
        return SupplierDaoMem.getInstance();

    }
}
