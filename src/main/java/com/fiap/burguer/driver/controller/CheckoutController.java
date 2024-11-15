package com.fiap.burguer.driver.controller;
import com.fiap.burguer.api.CheckoutApi;
import com.fiap.burguer.driver.presenters.CheckoutPresenter;
import com.fiap.burguer.driver.dto.CheckoutResponse;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.application.usecases.CheckoutUseCases;
import com.fiap.burguer.core.domain.CheckOut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class CheckoutController implements CheckoutApi {

    private final CheckoutUseCases checkoutUseCases;

    public CheckoutController(CheckoutUseCases checkoutUseCases) {
        this.checkoutUseCases = checkoutUseCases;
    }

    @Override
    public ResponseEntity<?> getCheckoutById(int id) {
        CheckOut checkout = checkoutUseCases.findById(id);
        return new ResponseEntity<>(checkout, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> createCheckout(int id, StatusOrder statusOrder) {
        CheckOut checkout = checkoutUseCases.createCheckout(id, statusOrder);
        return new ResponseEntity<>(checkout, HttpStatus.CREATED);
    }
}