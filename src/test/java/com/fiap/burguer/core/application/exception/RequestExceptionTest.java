package com.fiap.burguer.core.application.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.junit.jupiter.api.Assertions.*;

class RequestExceptionTest {

    @Test
    void testRequestException() {
        String message = "Bad request";
        RequestException exception = new RequestException(message);

        assertEquals(message, exception.getMessage());

        ResponseStatus responseStatus = RequestException.class.getAnnotation(ResponseStatus.class);
        assertNotNull(responseStatus);
        assertEquals(HttpStatus.BAD_REQUEST, responseStatus.value());
    }
}
