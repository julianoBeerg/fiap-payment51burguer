package com.fiap.burguer.core.application.exception;

public class ImpossibleToCheckoutException extends RuntimeException{

    public ImpossibleToCheckoutException(String errorMessage){
        super(errorMessage);
    }
}
