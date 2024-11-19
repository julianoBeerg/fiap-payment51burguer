package com.fiap.burguer.core.application.usecases;

import com.fiap.burguer.core.domain.CheckOut;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.infraestructure.repository.CheckOutRepository;
import com.fiap.burguer.infraestructure.entities.CheckOutEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CheckoutUseCases {

    private final CheckOutRepository checkOutRepository;

    public CheckoutUseCases(CheckOutRepository checkOutRepository) {
        this.checkOutRepository = checkOutRepository;
    }

    public CheckOut findByOrderId(int orderId) {
        Optional<CheckOutEntity> optionalEntity = checkOutRepository.findByOrderId(orderId);

        return optionalEntity.map(this::mapToDomain).orElse(null);

    }

    public CheckOut createCheckout(int orderId, StatusOrder statusOrder) {
        CheckOutEntity checkoutEntity = new CheckOutEntity();
        checkoutEntity.setOrderId(orderId);
        checkoutEntity.setPaymentStatus(statusOrder);

        checkOutRepository.save(checkoutEntity);

        return mapToDomain(checkoutEntity);
    }

    private CheckOut mapToDomain(CheckOutEntity entity) {
        CheckOut checkout = new CheckOut();
        checkout.setOrderId(entity.getOrderId());
        checkout.setPaymentStatus(entity.getPaymentStatus());
        return checkout;
    }
}
