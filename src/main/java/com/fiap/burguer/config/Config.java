package com.fiap.burguer.config;

import com.fiap.burguer.core.application.usecases.*;
import com.fiap.burguer.infraestructure.adapters.*;
import com.fiap.burguer.infraestructure.repository.CheckOutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Autowired
    AuthenticationAdapter authenticationAdapter;


    @Autowired
    CheckOutRepository checkOutRepository;

    @Autowired
    OrderAdapter orderAdapter;

    @Bean
    public CheckoutUseCases getCheckoutService() {
        return new CheckoutUseCases(checkOutRepository, authenticationAdapter,orderAdapter);
    }


}