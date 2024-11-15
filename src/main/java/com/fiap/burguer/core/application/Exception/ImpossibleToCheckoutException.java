package com.fiap.burguer.core.application.Exception;

public class ImpossibleToCheckoutException extends RuntimeException{

    public ImpossibleToCheckoutException(String errorMessage){
        super(errorMessage);
    }
}
