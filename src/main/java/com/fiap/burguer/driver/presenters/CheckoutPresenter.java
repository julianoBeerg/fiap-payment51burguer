package com.fiap.burguer.driver.presenters;
import com.fiap.burguer.driver.dto.CheckoutResponse;
import com.fiap.burguer.core.domain.CheckOut;

public class CheckoutPresenter {

    public static CheckoutResponse mapCheckoutToResponse(CheckOut checkOut) {
        if (checkOut == null) {
            return null;
        }
        CheckoutResponse responseCheckout = new CheckoutResponse();
        responseCheckout.setId(checkOut.getOrderId());
        responseCheckout.setPaymentStatus(checkOut.getPaymentStatus());
        responseCheckout.setDateCreated(checkOut.getDateCreated());
        responseCheckout.setTotalPrice(checkOut.getTotalPrice());
        responseCheckout.setCpf(checkOut.getCpf());
        responseCheckout.setClientId(checkOut.getClientId());

        return responseCheckout;
    }
}