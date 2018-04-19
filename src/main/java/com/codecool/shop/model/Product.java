package com.codecool.shop.model;

import java.util.Currency;

public class Product extends BaseModel {

    private float defaultPrice;
    private Currency defaultCurrency;
    private ProductCategory productCategory;
    private Supplier supplier;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (Float.compare(product.defaultPrice, defaultPrice) != 0) return false;
        if (!defaultCurrency.equals(product.defaultCurrency)) return false;
        if (!productCategory.getName().equals(product.productCategory.getName())) return false;
        return supplier.getName().equals(product.supplier.getName());
    }

    public Product(String name, float defaultPrice, String currencyString, String description, ProductCategory productCategory, Supplier supplier) {
        super(name, description);
        this.setPrice(defaultPrice, currencyString);
        this.setSupplier(supplier);
        this.setProductCategory(productCategory);
    }

    public Product(int id, String name, float defaultPrice, String currencyString, String description, ProductCategory productCategory, Supplier supplier){
        super(name, description);
        this.setPrice(defaultPrice, currencyString);
        this.setSupplier(supplier);
        this.setProductCategory(productCategory);
        this.id = id;
    }

    public float getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(float defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public Currency getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(Currency defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public String getPrice() {
        return String.valueOf(this.defaultPrice) + " " + this.defaultCurrency.toString();
    }

    public void setPrice(float price, String currency) {
        this.defaultPrice = price;
        this.defaultCurrency = Currency.getInstance(currency);
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
        this.productCategory.addProduct(this);
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
        this.supplier.addProduct(this);
    }

    @Override
    public String toString() {
        return String.format(
                        "name: %1$s, " +
                        "defaultPrice: %2$f, " +
                        "defaultCurrency: %3$s, " +
                        "productCategory: %4$s, " +
                        "supplier: %5$s",
                this.name,
                this.defaultPrice,
                this.defaultCurrency.toString(),
                this.productCategory.getName(),
                this.supplier.getName());
    }
}