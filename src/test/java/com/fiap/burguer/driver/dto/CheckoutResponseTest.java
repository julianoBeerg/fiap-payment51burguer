package com.fiap.burguer.driver.dto;

import com.fiap.burguer.core.application.enums.StatusOrder;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutResponseTest {

    @Test
    void testConstructorAndGetters() {
        int id = 1;
        LocalDateTime dateCreated = LocalDateTime.now();
        StatusOrder status = StatusOrder.WAITINGPAYMENT;
        double totalPrice = 99.99;
        Integer clientId = 123;
        String cpf = "12345678901";

        CheckoutResponse response = new CheckoutResponse(id, dateCreated, status, totalPrice, clientId, cpf);

        assertEquals(id, response.getId());
        assertEquals(dateCreated, response.getDateCreated());
        assertEquals(status, response.getPaymentStatus());
        assertEquals(totalPrice, response.getTotalPrice());
        assertEquals(clientId, response.getClientId());
        assertEquals(cpf, response.getCpf());
    }

    @Test
    void testSetters() {
        CheckoutResponse response = new CheckoutResponse();

        response.setId(2);
        response.setDateCreated(LocalDateTime.now());
        response.setPaymentStatus(StatusOrder.WAITINGPAYMENT);
        response.setTotalPrice(49.99);
        response.setClientId(456);
        response.setCpf("98765432100");

        assertEquals(2, response.getId());
        assertEquals(StatusOrder.WAITINGPAYMENT, response.getPaymentStatus());
        assertEquals(49.99, response.getTotalPrice());
        assertEquals(456, response.getClientId());
        assertEquals("98765432100", response.getCpf());
    }
}
