package com.fiap.burguer.infraestructure.mappers;

import com.fiap.burguer.core.domain.CheckOut;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.infraestructure.entities.CheckOutEntity;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class CheckOutMapperTest {

    @Test
    void testToDomainWithValidEntity() {
        CheckOutEntity entity = new CheckOutEntity();
        entity.setOrderId(100);
        entity.setPaymentStatus(StatusOrder.WAITINGPAYMENT);
        entity.setTotalPrice(99.99);
        entity.setClientId(200);
        entity.setCpf("12345678901");
        entity.setDateCreated(LocalDateTime.now());

        CheckOut domain = CheckOutMapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(entity.getOrderId(), domain.getOrderId());
        assertEquals(entity.getPaymentStatus(), domain.getPaymentStatus());
        assertEquals(entity.getTotalPrice(), domain.getTotalPrice());
        assertEquals(entity.getClientId(), domain.getClientId());
        assertEquals(entity.getCpf(), domain.getCpf());
        assertEquals(entity.getDateCreated(), domain.getDateCreated());
    }

    @Test
    void testToDomainWithNullEntity() {
        CheckOut domain = CheckOutMapper.toDomain(null);
        assertNull(domain, "O objeto convertido deveria ser null.");
    }

    @Test
    void testToEntityWithValidDomain() {
        CheckOut domain = CheckOut.createCheckOut();
        domain.setId("1");
        domain.setOrderId(100);
        domain.setPaymentStatus(StatusOrder.WAITINGPAYMENT);
        domain.setTotalPrice(99.99);
        domain.setClientId(200);
        domain.setCpf("12345678901");
        domain.setDateCreated(LocalDateTime.now());

        CheckOutEntity entity = CheckOutMapper.toEntity(domain);

        assertNotNull(entity);
        assertEquals(domain.getOrderId(), entity.getOrderId());
        assertEquals(domain.getPaymentStatus(), entity.getPaymentStatus());
        assertEquals(domain.getTotalPrice(), entity.getTotalPrice());
        assertEquals(domain.getClientId(), entity.getClientId());
        assertEquals(domain.getCpf(), entity.getCpf());
        assertEquals(domain.getDateCreated(), entity.getDateCreated());
    }

    @Test
    void testToEntityWithNullDomain() {
        CheckOutEntity entity = CheckOutMapper.toEntity(null);
        assertNull(entity, "O objeto convertido deveria ser null.");
    }
}
