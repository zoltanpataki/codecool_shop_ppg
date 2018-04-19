package com.codecool.shop.dao;

import com.codecool.shop.Config;
import com.codecool.shop.dao.implementation.DaoException;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class ProductDaoTest<T extends ProductDao> {

    private T instance;
    private ProductCategory pcGame;
    private Supplier codeCool;
    private Product snake;

    protected abstract T createInstance();

    @BeforeEach
    void setUp() {
        Config.setEnvironment("test");
        instance = createInstance();
        codeCool = new Supplier(1,"Codecool", "Codecool's Powerpuff Girlsy stuff");
        pcGame = new ProductCategory(1,"PC Game", "gaming", "Category of PC games");
        snake = new Product("PPG Snake", 25, "USD", "THE BEST SNAKE GAME EVER", pcGame, codeCool);
    }

    @AfterEach
    void tearDown(){
        Config.setEnvironment("prod");
    }

    @Test
    void add() throws DaoException {
        instance.add(snake);
        int numOfProducts = instance.getAll().size();
        int id = instance.getAll().get(numOfProducts-1).getId();
        assertEquals(snake, instance.getAll().get(numOfProducts-1));
        instance.remove(id);
    }

    @Test
    void find() throws DaoException {
        instance.add(snake);
        int id = instance.getAll().get(instance.getAll().size()-1).getId();
        assertEquals(instance.getAll().get(0), instance.find(id));
        instance.remove(id);
    }

    @Test
    void remove() throws DaoException {
        instance.add(snake);
        int sizeBeforeRemoving = instance.getAll().size();
        int id = instance.getAll().get(sizeBeforeRemoving-1).getId();
        instance.remove(id);
        int sizeAfterRemoving = instance.getAll().size();
        assertEquals(sizeBeforeRemoving-1, sizeAfterRemoving);
    }

    @Test
    void getAll() throws DaoException {
        instance.add(snake);
        int numOfProducts = instance.getAll().size();
        int id = instance.getAll().get(numOfProducts - 1).getId();
        assertEquals(1, instance.getAll().size());
        instance.remove(id);
    }

    @Test
    void getByCategory() throws DaoException {
        instance.add(snake);
        List<Product> expectedList = new ArrayList<>();
        int numOfProducts = instance.getAll().size();
        int id = instance.getAll().get(numOfProducts - 1).getId();
        expectedList.add(snake);
        assertEquals(expectedList, instance.getBy(pcGame));
        instance.remove(id);
    }

    @Test
    void getBySupplier() throws DaoException {
        instance.add(snake);
        List<Product> expectedList = new ArrayList<>();
        int numOfProducts = instance.getAll().size();
        int id = instance.getAll().get(numOfProducts - 1).getId();
        expectedList.add(snake);
        assertEquals(expectedList, instance.getBy(codeCool));
        instance.remove(id);
    }


}