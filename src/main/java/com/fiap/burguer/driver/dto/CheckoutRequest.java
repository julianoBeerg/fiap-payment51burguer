package com.fiap.burguer.driver.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class CheckoutRequest {

    @NotNull(message = "O campo orderId é obrigatório")
    private Integer orderId;

    @NotNull(message = "O campo totalPrice é obrigatório")
    @DecimalMin(value = "0.01", message = "O preço total deve ser maior que zero")
    private Double totalPrice;

    public CheckoutRequest(Integer orderId, Double totalPrice){
        this.orderId = orderId;
        this.totalPrice = totalPrice;
    }
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

}
