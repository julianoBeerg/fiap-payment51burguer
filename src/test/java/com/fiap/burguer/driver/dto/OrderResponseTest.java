package com.fiap.burguer.driver.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class OrderResponseTest {

    private OrderResponse orderResponse;

    @BeforeEach
    void setUp() {
        orderResponse = new OrderResponse(1, "WAITINGPAYMENT", 100.0, new Date(), 10.5);
    }

    @Test
    void testOrderResponseConstructor() {
        assertEquals(1, orderResponse.getOrderId());
        assertEquals("WAITINGPAYMENT", orderResponse.getStatus());
        assertEquals(100.0, orderResponse.getTotalPrice());
        assertNotNull(orderResponse.getDateCreated());
        assertEquals(10.5, orderResponse.getTimeWaitingOrder());
    }

    @Test
    void testSettersAndGetters() {
        orderResponse.setOrderId(2);
        orderResponse.setStatus("APPROVEDPAYMENT");
        orderResponse.setTotalPrice(200.0);
        orderResponse.setDateCreated(new Date());
        orderResponse.setTimeWaitingOrder(15.0);

        assertEquals(2, orderResponse.getOrderId());
        assertEquals("APPROVEDPAYMENT", orderResponse.getStatus());
        assertEquals(200.0, orderResponse.getTotalPrice());
        assertNotNull(orderResponse.getDateCreated());
        assertEquals(15.0, orderResponse.getTimeWaitingOrder());
    }

    @Test
    void testDefaultConstructor() {
        OrderResponse defaultOrderResponse = new OrderResponse();
        assertEquals(0, defaultOrderResponse.getOrderId());
        assertNull(defaultOrderResponse.getStatus());
        assertEquals(0.0, defaultOrderResponse.getTotalPrice());
        assertNull(defaultOrderResponse.getDateCreated());
        assertEquals(0.0, defaultOrderResponse.getTimeWaitingOrder());
    }
}
