package com.fiap.burguer.infraestructure.adapters;

import com.fiap.burguer.core.application.exception.RequestUnauthorized;
import com.fiap.burguer.core.application.utils.JwtUtil;
import com.fiap.burguer.utils.TestTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationAdapterTest {

    private AuthenticationAdapter authenticationAdapter;
    private String validToken;
    private String invalidToken;
    private String nonAdminToken;

    @BeforeEach
    void setUp() {
        authenticationAdapter = new AuthenticationAdapter(new JwtUtil());
        validToken = TestTokenUtil.generateValidMockToken(Map.of(
                "cpf", "12345678901",
                "name", "Mock User",
                "id", 123,
                "isAdmin", true,
                "exp", 1893456000,
                "email", "mock@user.com"
        ));

        invalidToken = TestTokenUtil.generateValidMockToken(Map.of(
                "cpf", "12345678901",
                "name", "Mock User",
                "id", 123,
                "isAdmin", true,
                "exp", 1656633600,
                "email", "mock@user.com"
        ));
        nonAdminToken = TestTokenUtil.generateValidMockToken(Map.of(
                "cpf", "12345678901",
                "name", "Mock User",
                "id", 123,
                "isAdmin", false,
                "exp", 1656633600,
                "email", "mock@user.com"
        ));
    }

    @Test
    void testValidateAuthorizationHeader_withValidToken_shouldPass() {
        assertDoesNotThrow(() -> {
            authenticationAdapter.validateAuthorizationHeader(validToken);
        });
    }

    @Test
    void testValidateAuthorizationHeader_withNullHeader_shouldThrowException() {
        Exception exception = assertThrows(RequestUnauthorized.class, () -> {
            authenticationAdapter.validateAuthorizationHeader(null);
        });
        assertEquals("Token não fornecido ou inválido.", exception.getMessage());
    }

    @Test
    void testValidateAuthorizationHeader_withEmptyHeader_shouldThrowException() {
        Exception exception = assertThrows(RequestUnauthorized.class, () -> {
            authenticationAdapter.validateAuthorizationHeader(" ");
        });
        assertEquals("Token não fornecido ou inválido.", exception.getMessage());
    }

    @Test
    void testValidateAuthorizationHeader_withInvalidPrefix_shouldThrowException() {
        Exception exception = assertThrows(RequestUnauthorized.class, () -> {
            authenticationAdapter.validateAuthorizationHeader("Invalid token");
        });
        assertEquals("Tipo de token inválido. Esperado Bearer.", exception.getMessage());
    }

    @Test
    void testValidateIsAdminAccess_withAdminToken_shouldPass() {
        assertDoesNotThrow(() -> {
            authenticationAdapter.validateIsAdminAccess(validToken);
        });
    }

    @Test
    void testValidateIsAdminAccess_withNonAdminToken_shouldThrowException() {

        Exception exception = assertThrows(RequestUnauthorized.class, () -> {
            authenticationAdapter.validateIsAdminAccess(nonAdminToken);
        });

        assertEquals("Usuário não tem permissão para acessar essa API.", exception.getMessage());
    }

    @Test
    void testValidateIdUser_shouldReturnCorrectId() {
        Integer id = authenticationAdapter.getClientIdFromToken(validToken);
        assertEquals(123, id);
    }

    @Test
    void testGetClientIdFromToken_shouldReturnCorrectId() {
        Integer clientId = authenticationAdapter.getClientIdFromToken(validToken);
        assertEquals(123, clientId);
    }

    @Test
    void testGetCpfFromToken_shouldReturnCorrectCpf() {
        String cpf = authenticationAdapter.getCpfFromToken(validToken);
        assertEquals("12345678901", cpf);
    }

    @Test
    void testValidateIsTokenExpired_withNonExpiredToken_shouldReturnFalse() {
        Boolean isExpired = authenticationAdapter.validateIsTokenExpired(invalidToken);
        assertTrue(isExpired);
    }
}
