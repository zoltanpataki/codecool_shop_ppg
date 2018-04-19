package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.LineItem;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartDaoMem implements CartDao {
    private static CartDaoMem instance = null;
    private double price;
    private int amount;
    private int user_id;
    private String status;
    private String date;
    private List<LineItem> DATA = new ArrayList<>();

    /* A private Constructor prevents any other class from instantiating.
     */
    private CartDaoMem() {
    }

    public static CartDaoMem getInstance() {
        if (instance == null) {
            instance = new CartDaoMem();
        }
        return instance;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getStatus() {
        return status;
    }


    public void setPrice(double price) {
        this.price = price;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public void removeLineItem(LineItem lineItem) {
        this.DATA.remove(lineItem);
    }

    @Override
    public void add(LineItem lineItem) {
        lineItem.setId(DATA.size() + 1);
        DATA.add(lineItem);
    }

    @Override
    public LineItem find(int id) {
        return DATA.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        DATA.remove(find(id));
    }

    @Override
    public List<LineItem> getAll() {
        return DATA;
    }
}
