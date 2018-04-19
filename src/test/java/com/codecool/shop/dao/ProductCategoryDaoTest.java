package com.codecool.shop.dao;

import com.codecool.shop.Config;
import com.codecool.shop.dao.implementation.DaoException;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class ProductCategoryDaoTest<T extends ProductCategoryDao> {

    private T instance;
    private ProductCategory pcGame = new ProductCategory("PC Game", "Gaming", "Category of PC games");


    protected abstract T createInstance();

    @BeforeEach
    void setUp() throws DaoException {
        Config.setEnvironment("test");
        instance = createInstance();
        ProductCategoryDao categoryDataStore = ProductCategoryDaoMem.getInstance();
        categoryDataStore.add(new ProductCategory(1, "PC Game", "Gaming", "Category of PC games"));
    }

    @AfterEach
    void tearDown(){
        Config.setEnvironment("prod");
    }

    @Test
    void add() throws DaoException {
        instance.add(pcGame);
        int numOfCategories = instance.getAll().size();
        int id = instance.getAll().get(numOfCategories-1).getId();
        assertEquals(pcGame, instance.getAll().get(numOfCategories-1));
        instance.remove(id);
    }

    @Test
    void find() throws DaoException {
        instance.add(pcGame);
        int id = instance.getAll().get(instance.getAll().size()-1).getId();
        assertEquals(instance.getAll().get(1), instance.find(id));
        instance.remove(id);
    }

    @Test
    void remove() throws DaoException {
        instance.add(pcGame);
        int sizeBeforeRemoving = instance.getAll().size();
        int id = instance.getAll().get(sizeBeforeRemoving-1).getId();
        instance.remove(id);
        int sizeAfterRemoving = instance.getAll().size();
        assertEquals(sizeBeforeRemoving-1, sizeAfterRemoving);
    }

    @Test
    void getAll() throws DaoException {
        instance.add(pcGame);
        int numOfCategories = instance.getAll().size();
        int id = instance.getAll().get(numOfCategories-1).getId();
        assertEquals(2, instance.getAll().size());
        instance.remove(id);
    }

}