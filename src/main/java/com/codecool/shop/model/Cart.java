package com.codecool.shop.model;

import java.util.ArrayList;

public class Cart {
    private double price;
    private int amount;
    private int user_id;
    private String status;
    private UserData userData = new UserData();


    private ArrayList<LineItem> lineItems = new ArrayList<>();

    public Cart(int user_id, String status) {
        this.user_id = user_id;
        this.status = status;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getStatus() {
        return status;
    }

    public void setLineItems(ArrayList<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public ArrayList<LineItem> getLineItems() {
        return this.lineItems;
    }

    public void addLineItem(LineItem lineItem) {
        this.lineItems.add(lineItem);
    }

    public void removeLineItem(LineItem lineItem) {
        this.lineItems.remove(lineItem);
    }

    public UserData getUserData() {
        return userData;
    }

    public double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
