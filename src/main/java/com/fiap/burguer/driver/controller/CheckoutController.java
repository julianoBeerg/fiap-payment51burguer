package com.fiap.burguer.driver.controller;

import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.application.usecases.CheckoutUseCases;
import com.fiap.burguer.driver.dto.CheckoutResponse;
import com.fiap.burguer.driver.dto.ErrorResponse;
import com.fiap.burguer.driver.presenters.CheckoutPresenter;
import com.fiap.burguer.api.CheckoutApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/checkout")
public class CheckoutController implements CheckoutApi {

    private final CheckoutUseCases checkoutUseCases;

    public CheckoutController(CheckoutUseCases checkoutUseCases) {
        this.checkoutUseCases = checkoutUseCases;
    }

    @Override
    public ResponseEntity<CheckoutResponse> getCheckoutByOrderId(int orderId) {
        try {
            CheckoutResponse response = checkoutUseCases.getCheckoutByOrderId(orderId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Override
    public ResponseEntity<?> searchCheckouts(Integer orderId, StatusOrder status, Integer clientId, String cpf, String authorizationHeader) {
        try {
            List<CheckoutResponse> responses = checkoutUseCases.searchCheckouts(orderId, status, clientId, cpf, authorizationHeader);
            return ResponseEntity.ok(responses);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(
                    HttpStatus.NOT_FOUND.value(),
                    e.getMessage(),
                    System.currentTimeMillis()
            ));
        }
    }

    @Override
    public ResponseEntity<?> createCheckout(int orderId,  String authorizationHeader) {
        StatusOrder statusOrder = StatusOrder.WAITINGPAYMENT;
        try {
            CheckoutResponse response = checkoutUseCases.createCheckout(orderId, statusOrder, authorizationHeader);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(),
                    System.currentTimeMillis()
            ));
        }
    }

    @Override
    public ResponseEntity<?> updateCheckoutStatus(int orderId, StatusOrder newStatus, String authorizationHeader) {
        try {
            CheckoutResponse response = checkoutUseCases.updateCheckoutStatus(orderId, newStatus, authorizationHeader);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(),
                    System.currentTimeMillis()
            ));
        }
    }
}
