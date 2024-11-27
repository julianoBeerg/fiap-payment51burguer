package com.fiap.burguer.core.application.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RequestUnauthorizedTest {

    @Test
    void testRequestUnauthorized() {
        String message = "Unauthorized access";
        RequestUnauthorized exception = new RequestUnauthorized(message);

        assertEquals(message, exception.getMessage());
    }
}
