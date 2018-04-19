package com.codecool.shop.dao;

import com.codecool.shop.model.LineItem;

import java.sql.Timestamp;
import java.util.List;

public interface CartDao {

    void add(LineItem lineItem);

    LineItem find(int id);

    void remove(int id);

    double getPrice();

    void setPrice(double price);

    int getAmount();

    void setAmount(int amount);

    int getUser_id();

    String getStatus();

    void removeLineItem(LineItem lineItem);

    List<LineItem> getAll();
}
