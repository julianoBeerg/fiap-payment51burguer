package com.fiap.burguer.core.domain;
import com.fiap.burguer.core.application.enums.StatusOrder;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class CheckOut implements Serializable {
    private int id;

    private Date dateCreated;

    private StatusOrder paymentStatus;

    private double totalPrice;

    private String transactId;

    public void generateTransactId() {
        if (this.transactId == null || this.transactId.isEmpty()) {
            this.transactId = UUID.randomUUID().toString();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
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

    public String getTransactId() {
        return transactId;
    }

    public void setTransactId(String transactId) {
        this.transactId = transactId;
    }
}