package com.codecool.shop.model;

import java.util.ArrayList;

public class ProductCategory extends BaseModel {
    private String department;
    private ArrayList<Product> products;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductCategory that = (ProductCategory) o;

        if (!department.equals(that.department)) return false;
        return products != null ? products.equals(that.products) : that.products == null;
    }

    @Override
    public int hashCode() {
        int result = department.hashCode();
        result = 31 * result + (products != null ? products.hashCode() : 0);
        return result;
    }

    public ProductCategory(String name, String department, String description) {
        super(name);
        this.department = department;
        this.description = description;
        this.products = new ArrayList<>();
    }

    public ProductCategory(int id, String name, String department, String description){
        super(name);
        this.department = department;
        this.description = description;
        this.products = new ArrayList<>();
        this.id = id;
    }


    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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
                        "department: %2$s, " +
                        "description: %3$s",
                this.name,
                this.department,
                this.description);
    }
}