package com.fiap.burguer.driver.controller;

import com.fiap.burguer.core.application.utils.JwtUtil;
import com.fiap.burguer.driver.dto.CheckoutResponse;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.application.usecases.CheckoutUseCases;
import com.fiap.burguer.driver.presenters.CheckoutPresenter;
import com.fiap.burguer.infraestructure.entities.CheckOutEntity;
import com.fiap.burguer.infraestructure.mappers.CheckOutMapper;
import com.fiap.burguer.infraestructure.repository.CheckOutRepository;
import com.fiap.burguer.api.CheckoutApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/checkout")
public class CheckoutController implements CheckoutApi {
    CheckOutRepository checkOutRepository;
    JwtUtil jwtUtil;

    public CheckoutController(  CheckOutRepository checkOutRepository, JwtUtil jwtUtil) {
        this.checkOutRepository = checkOutRepository;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<CheckoutResponse> getCheckoutByOrderId(int orderId) {
        Optional<CheckOutEntity> checkoutOpt = checkOutRepository.findByOrderId(orderId);
        if (checkoutOpt.isPresent()) {
            CheckOutEntity checkout = checkoutOpt.get();
            CheckoutResponse checkoutResponse = CheckoutPresenter.mapCheckoutToResponse(CheckOutMapper.toDomain(checkout));
            return ResponseEntity.ok(checkoutResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    public ResponseEntity<?> searchCheckouts(Integer orderId, StatusOrder status, Integer clientId, String cpf, String authorizationHeader) {

        Integer tokenClientId = jwtUtil.getIdFromToken(authorizationHeader);
        String tokenCpf = jwtUtil.getCpfFromToken(authorizationHeader);

        clientId = (clientId == null) ? tokenClientId : clientId;
        cpf = (cpf == null || cpf.isEmpty()) ? tokenCpf : cpf;

        Set<CheckOutEntity> checkouts = new HashSet<>();

        if (orderId != null) {
            Optional<CheckOutEntity> orderCheckout = checkOutRepository.findByOrderId(orderId);
            orderCheckout.ifPresent(checkouts::add);
        }

        if (status != null) {
            checkouts.addAll(checkOutRepository.findByPaymentStatus(status));
        }
        if (clientId != null) {
            checkouts.addAll(checkOutRepository.findByClientId(clientId));
        }
        if (cpf != null) {
            checkouts.addAll(checkOutRepository.findByCpf(cpf));
        }

        if (checkouts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum checkout encontrado!");
        }

        List<CheckoutResponse> checkoutResponses = new ArrayList<>();
        for (CheckOutEntity checkoutEntity : checkouts) {
            CheckoutResponse response = CheckoutPresenter.mapCheckoutToResponse(CheckOutMapper.toDomain(checkoutEntity));
            checkoutResponses.add(response);
        }

        return ResponseEntity.ok(checkoutResponses);
    }

    @Override
    public ResponseEntity<CheckoutResponse> createCheckout(int orderId, StatusOrder statusOrder,  String authorizationHeader) {
        Integer clientId = jwtUtil.getIdFromToken(authorizationHeader);
        String cpf = jwtUtil.getCpfFromToken(authorizationHeader);

        if (cpf != null) {
            cpf = cpf.replaceAll("[^0-9]", "");
        }

        CheckOutEntity checkoutEntity = new CheckOutEntity(
                orderId,
                0.0,
                statusOrder,
                clientId,
                cpf,
                LocalDateTime.now()

        );

        checkOutRepository.save(checkoutEntity);

        CheckoutResponse response = CheckoutPresenter.mapCheckoutToResponse(CheckOutMapper.toDomain(checkoutEntity));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

