package com.fiap.burguer.driver.dto;

import java.util.Date;

public class OrderResponse {
    private int orderId;
    private String status;
    private double totalPrice;
    private Date dateCreated;
    private double timeWaitingOrder;

    public OrderResponse(int id, String status, double totalPrice, Date dateCreated, double timeWaitingOrder) {
        this.orderId = id;
        this.status = status;
        this.totalPrice = totalPrice;
        this.dateCreated = dateCreated;
        this.timeWaitingOrder = timeWaitingOrder;
    }

    public OrderResponse() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public double getTimeWaitingOrder() {
        return timeWaitingOrder;
    }

    public void setTimeWaitingOrder(double timeWaitingOrder) {
        this.timeWaitingOrder = timeWaitingOrder;
    }

}
