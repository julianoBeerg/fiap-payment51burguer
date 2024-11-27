package com.fiap.burguer.driver.handlers;

import com.fiap.burguer.core.application.utils.JwtUtil;
import com.fiap.burguer.infraestructure.adapters.AuthenticationAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import com.fiap.burguer.core.application.exception.RequestUnauthorized;

class GlobalExceptionHandlerTest{

    @Mock
    private AuthenticationAdapter authenticationAdapter;

    @Mock
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = Mockito.mock(JwtUtil.class);
        authenticationAdapter = new AuthenticationAdapter(jwtUtil);


    }

    @Test
    void shouldThrowExceptionWhenAuthorizationHeaderIsNull() {
        Exception exception = assertThrows(RequestUnauthorized.class,
                () -> authenticationAdapter.validateAuthorizationHeader(null));

        assert exception.getMessage().equals("Token não fornecido ou inválido.");
    }

    @Test
    void shouldThrowExceptionWhenTokenIsExpired() {

        RequestUnauthorized exception = assertThrows(
                RequestUnauthorized.class,
                () -> authenticationAdapter.validateAuthorizationHeader("")
        );
        assertEquals("Token não fornecido ou inválido.", exception.getMessage());
    }

}
