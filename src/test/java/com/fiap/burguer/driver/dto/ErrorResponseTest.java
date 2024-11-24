package com.fiap.burguer.driver.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {

    @Test
    void testConstructorAndGetters() {
        int status = 400;
        String message = "Erro de validação";
        long timestamp = System.currentTimeMillis();

        ErrorResponse errorResponse = new ErrorResponse(status, message, timestamp);

        assertEquals(status, errorResponse.getStatus());
        assertEquals(message, errorResponse.getMessage());
        assertEquals(timestamp, errorResponse.getTimestamp());
    }

    @Test
    void testSetters() {
        ErrorResponse errorResponse = new ErrorResponse(0, null, 0L);

        errorResponse.setStatus(404);
        errorResponse.setMessage("Recurso não encontrado");
        errorResponse.setTimestamp(123456789L);

        assertEquals(404, errorResponse.getStatus());
        assertEquals("Recurso não encontrado", errorResponse.getMessage());
        assertEquals(123456789L, errorResponse.getTimestamp());
    }
}
