package com.fiap.burguer.driver.dto;

import com.fiap.burguer.core.application.enums.StatusOrder;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class CheckoutRequest {

    @NotNull(message = "O campo orderId é obrigatório")
    private Integer orderId;

    @NotNull(message = "O campo totalPrice é obrigatório")
    @DecimalMin(value = "0.01", message = "O preço total deve ser maior que zero")
    private Double totalPrice;

    @NotNull(message = "O campo paymentStatus é obrigatório")
    private StatusOrder paymentStatus;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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
}
