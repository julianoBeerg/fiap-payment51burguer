package com.fiap.burguer.core.domain;
import com.fiap.burguer.core.application.enums.StatusOrder;

import java.time.LocalDateTime;

public class CheckOut {

    private String id;
    private int orderId;
    private LocalDateTime dateCreated;
    private StatusOrder paymentStatus;
    private Double totalPrice;
    private Integer clientId;
    private String cpf;

    private CheckOut() {
    }

    public static CheckOut createCheckOut() {
        return new CheckOut();
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public StatusOrder getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(StatusOrder paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}