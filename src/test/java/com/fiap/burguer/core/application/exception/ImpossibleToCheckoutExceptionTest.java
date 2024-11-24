package com.fiap.burguer.core.application.exception;

import com.fiap.burguer.core.application.Exception.ImpossibleToCheckoutException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ImpossibleToCheckoutExceptionTest {

    @Test
    void testImpossibleToCheckoutException() {
        String errorMessage = "Checkout is impossible";
        ImpossibleToCheckoutException exception = new ImpossibleToCheckoutException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }
}

