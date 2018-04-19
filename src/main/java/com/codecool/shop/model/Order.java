package com.codecool.shop.model;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.eclipse.jetty.util.StringUtil.toLong;

public class Order extends BaseModel{
    private double price;
    private int amount;
    private int user_id;
    private String status;
    private Timestamp date;
    private UserData userData = new UserData();


    private List<LineItem> lineItems = new ArrayList<>();

    public Order(int user_id, String status) {
        this.user_id = user_id;
        this.status = status;
        this.date = setDate();
    }

    public int getUser_id() {
        return user_id;
    }

    public String getStatus() {
        return status;
    }

    public Timestamp getDate() {
        return date;
    }

    public Timestamp setDate() {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String date = dateFormat.format(new Date());
            Date parsedDate = dateFormat.parse(date);
            return new Timestamp(parsedDate.getTime());
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public List<LineItem> getLineItems() {
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

    public String toString() {
        return String.format("id: %1$d, " +
                        "name: %2$s, " +
                        "description: %3$s",
                this.id,
                this.name,
                this.description
        );
    }
}