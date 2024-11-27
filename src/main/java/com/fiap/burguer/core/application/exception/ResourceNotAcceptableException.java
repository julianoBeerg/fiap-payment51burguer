package com.fiap.burguer.core.application.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class ResourceNotAcceptableException extends RuntimeException {
    public ResourceNotAcceptableException(String message) {
        super(message);
    }
}