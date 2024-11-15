package com.fiap.burguer.driver.presenters;
import com.fiap.burguer.driver.dto.CheckoutResponse;
import com.fiap.burguer.core.domain.CheckOut;

public class CheckoutPresenter {

    public static CheckoutResponse mapCheckoutToResponse(CheckOut checkOut) {
        if (checkOut == null) {
            return null;
        }
        CheckoutResponse responseCheckout = new CheckoutResponse();
        responseCheckout.setId(checkOut.getId());
        responseCheckout.setTransactId(checkOut.getTransactId());
        responseCheckout.setPayment_status(checkOut.getPaymentStatus());
        responseCheckout.setDateCreated(checkOut.getDateCreated());
        responseCheckout.setTotalPrice(checkOut.getTotalPrice());

        return responseCheckout;
    }
}