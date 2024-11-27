package com.fiap.burguer.core.application.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.junit.jupiter.api.Assertions.*;

class ResourceNotAcceptableExceptionTest {

    @Test
    void testResourceNotAcceptableException() {
        String message = "Resource not acceptable";
        ResourceNotAcceptableException exception = new ResourceNotAcceptableException(message);

        assertEquals(message, exception.getMessage());

        ResponseStatus responseStatus = ResourceNotAcceptableException.class.getAnnotation(ResponseStatus.class);
        assertNotNull(responseStatus);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, responseStatus.value());
    }
}
