package com.fiap.burguer.infraestructure.entities;

import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.domain.CheckOut;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CheckOutEntityTest {

    @Test
    void testGetterAndSetter() {
        CheckOutEntity entity = new CheckOutEntity();

        entity.setOrderId(123);
        entity.setTotalPrice(250.75);
        entity.setPaymentStatus(StatusOrder.WAITINGPAYMENT);
        entity.setClientId(1);
        entity.setCpf("12345678901");
        entity.setDateCreated(LocalDateTime.now());

        assertEquals(123, entity.getOrderId());
        assertEquals(250.75, entity.getTotalPrice());
        assertEquals(StatusOrder.WAITINGPAYMENT, entity.getPaymentStatus());
        assertEquals(1, entity.getClientId());
        assertEquals("12345678901", entity.getCpf());
        assertNotNull(entity.getDateCreated());
    }

    @Test
    void testNoArgsConstructor() {
        CheckOutEntity entity = new CheckOutEntity();

        assertNull(entity.getId());
        assertEquals(0, entity.getOrderId());
        assertNull(entity.getPaymentStatus());
        assertNull(entity.getClientId());
        assertNull(entity.getCpf());
        assertNull(entity.getDateCreated());
    }

    @Test
    void testSettersAndGetters() {
        CheckOutEntity entity = new CheckOutEntity();

        entity.setOrderId(100);
        entity.setTotalPrice(150.50);
        entity.setPaymentStatus(StatusOrder.WAITINGPAYMENT);
        entity.setClientId(2);
        entity.setCpf("98765432100");
        entity.setDateCreated(LocalDateTime.now());

        assertEquals(100, entity.getOrderId());
        assertEquals(150.50, entity.getTotalPrice());
        assertEquals(StatusOrder.WAITINGPAYMENT, entity.getPaymentStatus());
        assertEquals(2, entity.getClientId());
        assertEquals("98765432100", entity.getCpf());
        assertNotNull(entity.getDateCreated());
    }

    @Test
    void testToDomain() {
        LocalDateTime date = LocalDateTime.now();
        CheckOutEntity entity = new CheckOutEntity(123, 250.75, StatusOrder.WAITINGPAYMENT, 1, "12345678901", date);

        CheckOut checkout = entity.toDomain();

        assertEquals(123, checkout.getOrderId());
        assertEquals(250.75, checkout.getTotalPrice());
        assertEquals(StatusOrder.WAITINGPAYMENT, checkout.getPaymentStatus());
        assertEquals(1, checkout.getClientId());
        assertEquals("12345678901", checkout.getCpf());
        assertEquals(date, checkout.getDateCreated());
    }


    @Test
    void testAllArgsConstructor() {
        LocalDateTime fixedDate = LocalDateTime.of(2024, 11, 27, 10, 30, 0, 0);

        CheckOutEntity entity = new CheckOutEntity(123, 250.75, StatusOrder.WAITINGPAYMENT, 1, "12345678901", fixedDate);

        assertEquals(123, entity.getOrderId());
        assertEquals(250.75, entity.getTotalPrice());
        assertEquals(StatusOrder.WAITINGPAYMENT, entity.getPaymentStatus());
        assertEquals(1, entity.getClientId());
        assertEquals("12345678901", entity.getCpf());
        assertEquals(fixedDate, entity.getDateCreated());
    }

    @Test
    void testNotEquals() {
        CheckOutEntity entity1 = new CheckOutEntity(123, 250.75, StatusOrder.WAITINGPAYMENT, 1, "12345678901", LocalDateTime.now());
        CheckOutEntity entity2 = new CheckOutEntity(124, 300.00, StatusOrder.APPROVEDPAYMENT, 2, "98765432100", LocalDateTime.now());

        assertNotEquals(entity1, entity2);

        assertNotEquals(entity1.hashCode(), entity2.hashCode());
    }

}
