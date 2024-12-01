package com.fiap.burguer.driver.dto;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CheckoutRequestTest {

    @Test
    void testGetSetOrderId() {
        CheckoutRequest checkoutRequest = new CheckoutRequest(1, 100.0);

        assertEquals(1, checkoutRequest.getOrderId(), "O getter para orderId não retornou o valor correto.");

        checkoutRequest.setOrderId(2);

        assertEquals(2, checkoutRequest.getOrderId(), "O setter para orderId não atualizou o valor corretamente.");
    }

    @Test
    void testGetSetTotalPrice() {
        CheckoutRequest checkoutRequest = new CheckoutRequest(1, 100.0);

        assertEquals(100.0, checkoutRequest.getTotalPrice(), "O getter para totalPrice não retornou o valor correto.");

        checkoutRequest.setTotalPrice(150.0);

        assertEquals(150.0, checkoutRequest.getTotalPrice(), "O setter para totalPrice não atualizou o valor corretamente.");
    }
}
