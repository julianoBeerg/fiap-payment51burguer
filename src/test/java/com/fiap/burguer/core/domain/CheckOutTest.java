package com.fiap.burguer.core.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CheckOutTest {

    @Test
    void testSetIdAndGetId() {
        CheckOut checkOut = CheckOut.createCheckOut();

        String expectedId = "12345";
        checkOut.setId(expectedId);

        String actualId = checkOut.getId();

        assertEquals(expectedId, actualId, "O ID não foi recuperado corretamente.");
    }
}

