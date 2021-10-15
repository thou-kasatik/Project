package com.company.models;

public class Order {

    public static enum deliveryType {DeliveryToHouse, SelfPickUp}
    private int orderNumber;
    private int customerId;
    private String data;
    private String deliveryAddress;
    private int deliveryCost;
    public static enum paymentType {Cash, ByCard}
    public static enum status {InStock, OnTheWay, Delivered}

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public int getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(int deliveryCost) {
        this.deliveryCost = deliveryCost;
    }
}