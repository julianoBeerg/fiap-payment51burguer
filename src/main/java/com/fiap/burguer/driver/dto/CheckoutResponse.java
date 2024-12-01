package com.fiap.burguer.driver.dto;

import com.fiap.burguer.core.application.enums.StatusOrder;
import java.time.LocalDateTime;

public class CheckoutResponse {

    private int id;
    private LocalDateTime dateCreated;
    private StatusOrder paymentStatus;
    private double totalPrice;
    private Integer clientId;
    private String cpf;

    public CheckoutResponse(int id,
                            LocalDateTime dateCreated,
                            StatusOrder paymentStatus,
                            double totalPrice,
                            Integer clientId,
                            String cpf) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.paymentStatus = paymentStatus;
        this.totalPrice = totalPrice;
        this.clientId = clientId;
        this.cpf = cpf;
    }

    public CheckoutResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
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
}
