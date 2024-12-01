package com.fiap.burguer.infraestructure.entities;

import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.domain.CheckOut;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "checkout_order")
@NoArgsConstructor
public class CheckOutEntity {

    @Id
    private String id;

    @Field("order_id")
    private int orderId;

    @Field("client_id")
    private Integer clientId;

    @Field("date_created")
    private LocalDateTime dateCreated;

    @Field("cpf")
    private String cpf;

    @Field("status")
    private StatusOrder paymentStatus;

    @Field("total_price")
    private double totalPrice;


    public CheckOutEntity(int orderId, double totalPrice, StatusOrder paymentStatus, Integer clientId, String cpf, LocalDateTime dateCreated) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.paymentStatus = paymentStatus;
        this.clientId = clientId;
        this.cpf = cpf;
        this.dateCreated = dateCreated;
    }

    public CheckOut toDomain() {
        CheckOut checkout = CheckOut.createCheckOut();
        checkout.setOrderId(this.orderId);
        checkout.setTotalPrice(this.totalPrice);
        checkout.setPaymentStatus(this.paymentStatus);
        checkout.setClientId(this.clientId);
        checkout.setCpf(this.cpf);
        checkout.setDateCreated(this.dateCreated);
        return checkout;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public StatusOrder getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(StatusOrder paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}

