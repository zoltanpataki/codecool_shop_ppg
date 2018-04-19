package com.codecool.shop.model;

public class UserData {
    private String userName;
    private String eMail;
    private String phoneNumber;
    private String billingCountry;
    private String billingCity;
    private String billingZIPCode;
    private String billingAddress;
    private String shippingCountry;
    private String shippingCity;
    private String shippingZIPCode;
    private String shippingAddress;

    public UserData(){};

    public String geteMail() {
        return eMail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getBillingCountry() {
        return billingCountry;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public String getBillingZIPCode() {
        return billingZIPCode;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public String getShippingCountry() {
        return shippingCountry;
    }

    public String getShippingCity() {
        return shippingCity;
    }

    public String getShippingZIPCode() {
        return shippingZIPCode;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBillingCountry(String billingCountry) {
        this.billingCountry = billingCountry;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public void setBillingZIPCode(String billingZIPCode) {
        this.billingZIPCode = billingZIPCode;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public void setShippingCountry(String shippingCountry) {
        this.shippingCountry = shippingCountry;
    }

    public void setShippingCity(String shippingCity) {
        this.shippingCity = shippingCity;
    }

    public void setShippingZIPCode(String shippingZIPCode) {
        this.shippingZIPCode = shippingZIPCode;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
