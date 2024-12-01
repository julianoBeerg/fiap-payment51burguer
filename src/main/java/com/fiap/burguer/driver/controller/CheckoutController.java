package com.fiap.burguer.driver.controller;

import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.application.usecases.CheckoutUseCases;
import com.fiap.burguer.driver.dto.CheckoutRequest;
import com.fiap.burguer.driver.dto.CheckoutResponse;
import com.fiap.burguer.api.CheckoutApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<List<CheckoutResponse>> searchCheckouts(
            Integer orderId,
            StatusOrder status,
            Integer clientId,
            String cpf,
            String authorizationHeader) {
        try {
            List<CheckoutResponse> responses = checkoutUseCases.searchCheckouts(orderId, status, clientId, cpf, authorizationHeader);
            return ResponseEntity.ok(responses);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum checkout encontrado!", e);
        }
    }


    @Override
    public ResponseEntity<CheckoutResponse> createCheckout(
            CheckoutRequest checkoutRequest,
            String authorizationHeader) {
        try {
            StatusOrder statusOrder = StatusOrder.WAITINGPAYMENT;

            CheckoutResponse response = checkoutUseCases.createCheckout(checkoutRequest, statusOrder, authorizationHeader);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao processar pagamento!", e);
        }
    }


    @Override
    public ResponseEntity<CheckoutResponse> updateCheckoutStatus(
            int orderId,
            StatusOrder newStatus,
            String authorizationHeader) {
        try {
            CheckoutResponse response = checkoutUseCases.updateCheckoutStatus(orderId, newStatus, authorizationHeader);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado!", e);
        }
    }

}
