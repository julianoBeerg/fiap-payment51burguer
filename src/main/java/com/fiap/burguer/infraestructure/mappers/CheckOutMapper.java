package com.fiap.burguer.infraestructure.mappers;

import com.fiap.burguer.core.domain.CheckOut;
import com.fiap.burguer.infraestructure.entities.CheckOutEntity;

public class CheckOutMapper {

    public static CheckOut toDomain(CheckOutEntity entity) {
        if (entity == null) {
            return null;
        }

        CheckOut checkout = new CheckOut();
        checkout.setId(entity.getId());
        checkout.setOrderId(entity.getOrderId());
        checkout.setPaymentStatus(entity.getPaymentStatus());
        checkout.setTotalPrice(entity.getTotalPrice());
        checkout.setClientId(entity.getClientId());
        checkout.setCpf(entity.getCpf());
        checkout.setDateCreated(entity.getDateCreated());
        return checkout;
    }

    public static CheckOutEntity toEntity(CheckOut domain) {
        if (domain == null) {
            return null;
        }

        CheckOutEntity entity = new CheckOutEntity();
        entity.setOrderId(domain.getOrderId());
        entity.setPaymentStatus(domain.getPaymentStatus());
        entity.setTotalPrice(domain.getTotalPrice());
        entity.setClientId(domain.getClientId());
        entity.setCpf(domain.getCpf());
        entity.setDateCreated(domain.getDateCreated());
        return entity;
    }
}
