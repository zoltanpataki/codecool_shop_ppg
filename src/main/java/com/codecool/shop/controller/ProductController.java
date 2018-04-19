package com.codecool.shop.controller;

import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.*;

import spark.Request;
import spark.Response;
import spark.ModelAndView;


import java.util.HashMap;
import java.util.Map;


public class ProductController {

    public static boolean isLoggedIn = false;
    public static String username = "";

    public static ModelAndView renderProductsPerSupplier(Request req, Response res, String itemId, String type, double price, int amount) throws DaoException{
        ProductDao productDataStore = ProductDaoSql.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoSql.getInstance();
        SupplierDao supplierDataStore = SupplierDaoSql.getInstance();

        Map<Object, Object> params = new HashMap<>();
        params.put("supplier", supplierDataStore.find(Integer.parseInt(itemId)));
        params.put("allCategories", productCategoryDataStore.getAll());
        params.put("allSuppliers", supplierDataStore.getAll());
        params.put("price", price);
        params.put("amount", amount);
        params.put("name", supplierDataStore.find(Integer.parseInt(itemId)));
        params.put("products", productDataStore.getBy(supplierDataStore.find(Integer.parseInt(itemId))));
        params.put("isLoggedIn", isLoggedIn);
        params.put("username", username);
        return new ModelAndView(params, "product/index");

    }

    public static ModelAndView renderProductsPerCategory(Request req, Response res, String itemId, String type, double price, int amount) throws DaoException{
        ProductDao productDataStore = ProductDaoSql.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoSql.getInstance();
        SupplierDao supplierDataStore = SupplierDaoSql.getInstance();

        Map<Object, Object> params = new HashMap<>();
        params.put("category", productCategoryDataStore.find(Integer.parseInt(itemId)));
        params.put("allCategories", productCategoryDataStore.getAll());
        params.put("allSuppliers", supplierDataStore.getAll());
        params.put("price", price);
        params.put("amount", amount);
        params.put("name", productCategoryDataStore.find(Integer.parseInt(itemId)));
        params.put("products", productDataStore.getBy(productCategoryDataStore.find(Integer.parseInt(itemId))));
        params.put("isLoggedIn", isLoggedIn);
        params.put("username", username);
        return new ModelAndView(params, "product/index");

    }

    public static ModelAndView renderCartForm(Request req, Response res, double price, int amount) {
        CartDao cartDataStore = CartDaoMem.getInstance();

        Map params = new HashMap<>();
        params.put("products", cartDataStore.getAll());
        params.put("price", price);
        params.put("amount", amount);
        params.put("isLoggedIn", isLoggedIn);
        params.put("username", username);
        return new ModelAndView(params, "cart");
    }

    public static ModelAndView renderPaymentForm(int orderId, Request req, Response res, double price, int amount) {
        Map params = new HashMap<>();
        params.put("price", price);
        params.put("amount", amount);
        params.put("orderId", orderId);
        params.put("isLoggedIn", isLoggedIn);
        params.put("username", username);
        return new ModelAndView(params, "Payment");
    }

    public static ModelAndView renderSuccessPage(Request req, Response res) {
        Map empty = new HashMap<>();
        return new ModelAndView(empty, "success");
    }

    public static ModelAndView renderRegisterPage(Request req, Response res){
        Map empty = new HashMap<>();
        return new ModelAndView(empty,"register");
    }
    public static ModelAndView renderLoginPage(Request req, Response res){
        Map empty = new HashMap<>();
        return new ModelAndView(empty,"login");
    }

    public static ModelAndView renderCheckoutForm(Request req, Response res, int orderId, double price, int amount) {
        Map params = new HashMap<>();
        System.out.println("lófasz");
        params.put("price", price);
        params.put("amount", amount);
        params.put("orderId", orderId);
        params.put("isLoggedIn", isLoggedIn);
        return new ModelAndView(params, "checkout");
    }

    public static ModelAndView renderErrorPage(Request req, Response res) {
        Map map = new HashMap();
        return new ModelAndView(map, "error");
    }

    public static void setPriceAndAmount(CartDao cartDao){
        float price = 0;
        int amount = 0;
        for (int i = 0; i < cartDao.getAll().size(); i++){
            amount += cartDao.getAll().get(i).getQuantity();
            price += cartDao.getAll().get(i).getQuantity() * (cartDao.getAll().get(i).getDefaultPrice());
        }
        double roundedPrice = Math.round(price * 100.0) / 100.0;
        cartDao.setPrice(roundedPrice);
        cartDao.setAmount(amount);
    }

}
