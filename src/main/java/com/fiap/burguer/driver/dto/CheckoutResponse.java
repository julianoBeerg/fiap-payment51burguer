package com.fiap.burguer.driver.dto;
import com.fiap.burguer.core.application.enums.StatusOrder;
import java.util.Date;

public class CheckoutResponse {
    private int id;
    private Date dateCreated;
    private StatusOrder payment_status;
    private double totalPrice;
    private String transactId;

    public CheckoutResponse(int id,
                            Date dateCreated,
                            StatusOrder payment_status,
                            double totalPrice,
                            String transactId) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.payment_status = payment_status;
        this.totalPrice = totalPrice;
        this.transactId = transactId;
    }

    public CheckoutResponse() {
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

    public StatusOrder getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(StatusOrder payment_status) {
        this.payment_status = payment_status;
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
