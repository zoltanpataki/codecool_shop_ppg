package com.codecool.shop.model;

import java.util.ArrayList;


public class Supplier extends BaseModel {
    private ArrayList<Product> products;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Supplier supplier = (Supplier) o;

        return products != null ? products.equals(supplier.products) : supplier.products == null;
    }

    @Override
    public int hashCode() {
        return products != null ? products.hashCode() : 0;
    }

    public Supplier(String name, String description) {
        super(name);
        this.description = description;
        this.products = new ArrayList<>();
    }

    public Supplier(int id, String name, String description){
        super(name);
        this.description = description;
        this.products = new ArrayList<>();
        this.id = id;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList getProducts() {
        return this.products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public String toString() {
        return String.format(
                        "name: %1$s, " +
                        "description: %2$s",
                this.name,
                this.description
        );
    }
}