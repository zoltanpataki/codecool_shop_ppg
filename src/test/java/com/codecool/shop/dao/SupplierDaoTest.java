package com.codecool.shop.dao;

import com.codecool.shop.Config;
import com.codecool.shop.dao.implementation.DaoException;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class SupplierDaoTest<T extends SupplierDao> {

    private Supplier codeCool = new Supplier("Codecool", "Codecool's Powerpuff Girlsy stuff");
    private T instance;


    protected abstract T createInstance();


    @BeforeEach
    void setUp() throws DaoException {
        Config.setEnvironment("test");
        instance = createInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        supplierDataStore.add(new Supplier(1, "Codecool", "Codecool's Powerpuff Girlsy stuff"));
    }


    @AfterEach
    void tearDown(){
        Config.setEnvironment("prod");
    }


    @Test
    void add() throws DaoException {
        instance.add(codeCool);
        int numOfSuppliers = instance.getAll().size();
        int id = instance.getAll().get(numOfSuppliers-1).getId();
        assertEquals(codeCool, instance.getAll().get(numOfSuppliers-1));
        instance.remove(id);
    }

    @Test
    void find() throws DaoException {
        instance.add(codeCool);
        int id = instance.getAll().get(instance.getAll().size()-1).getId();
        assertEquals(instance.getAll().get(1), instance.find(id));
        instance.remove(id);
    }

    @Test
    void remove() throws DaoException {
        instance.add(codeCool);
        int sizeBeforeRemoving = instance.getAll().size();
        int id = instance.getAll().get(sizeBeforeRemoving-1).getId();
        instance.remove(id);
        int sizeAfterRemoving = instance.getAll().size();
        assertEquals(sizeBeforeRemoving-1, sizeAfterRemoving);
    }

    @Test
    void getAll() throws DaoException {
        instance.add(codeCool);
        int numOfCategories = instance.getAll().size();
        int id = instance.getAll().get(numOfCategories-1).getId();
        assertEquals(2, instance.getAll().size());
        instance.remove(id);
    }

}