package com.codecool.shop.model;

public class LineItem extends BaseModel {

    private int id;
    private int quantity;
    private float defaultPrice;
    private String productName;
    private int order_id;

    public LineItem(int id, String productName, int quantity, float defaultPrice) {
        this.id = id;
        this.quantity = quantity;
        this.defaultPrice = defaultPrice;
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getLineItemId() {
        return this.id;
    }

    public String getPrice() {
        return String.valueOf( Math.round(this.defaultPrice * this.quantity * 100.0) / 100.0);
    }

    public String getQuantityInString(){
        return String.valueOf(this.quantity) + " pcs";
    }

    public float getDefaultPrice() {
        return defaultPrice;
    }

    public String getProductName() {
        return productName;
    }
}
