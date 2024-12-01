package com.fiap.burguer.driver.controller;

import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.application.usecases.CheckoutUseCases;
import com.fiap.burguer.driver.dto.CheckoutRequest;
import com.fiap.burguer.driver.dto.CheckoutResponse;
import com.fiap.burguer.driver.dto.ErrorResponse;
import com.fiap.burguer.utils.TestTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckoutControllerTest {

    @InjectMocks
    private CheckoutController checkoutController;

    @Mock
    private CheckoutUseCases checkoutUseCases;

    private final String authorization = TestTokenUtil.generateValidMockToken(Map.of(
            "cpf", "12345678901",
            "name", "Mock User",
            "id", 123,
            "isAdmin", true,
            "exp", 1893456000,
            "email", "mock@user.com"
            ));

    @Test
    void testSearchCheckouts_Success() {
        Integer orderId = 1;
        StatusOrder status = StatusOrder.WAITINGPAYMENT;
        Integer clientId = 123;
        String cpf = "12345678901";

        CheckoutResponse response = new CheckoutResponse();
        response.setId(orderId);
        response.setPaymentStatus(status);
        List<CheckoutResponse> expectedResponses = List.of(response);

        when(checkoutUseCases.searchCheckouts(orderId, status, clientId, cpf, authorization))
                .thenReturn(expectedResponses);

        ResponseEntity<List<CheckoutResponse>> result = checkoutController.searchCheckouts(orderId, status, clientId, cpf, authorization);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
        assertEquals(orderId, result.getBody().getFirst().getId());
    }


    @Test
    void testSearchCheckouts_NotFound() {
        Integer orderId = 1;
        StatusOrder status = StatusOrder.WAITINGPAYMENT;
        Integer clientId = 123;
        String cpf = "12345678901";

        when(checkoutUseCases.searchCheckouts(orderId, status, clientId, cpf, authorization))
                .thenThrow(new RuntimeException("Nenhum checkout encontrado!"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> checkoutController.searchCheckouts(orderId, status, clientId, cpf, authorization));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Nenhum checkout encontrado!", exception.getReason());
    }

    @Test
    void testCreateCheckout_Success() {
        CheckoutRequest checkoutRequest = new CheckoutRequest(1, 0.1);

        CheckoutResponse expectedResponse = new CheckoutResponse();
        expectedResponse.setId(1);

        when(checkoutUseCases.createCheckout(checkoutRequest, StatusOrder.WAITINGPAYMENT, authorization))
                .thenReturn(expectedResponse);

        ResponseEntity<CheckoutResponse> response = checkoutController.createCheckout(checkoutRequest, authorization);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void testCreateCheckout_BadRequest() {
        CheckoutRequest checkoutRequest = new CheckoutRequest(1, 0.1);

        when(checkoutUseCases.createCheckout(checkoutRequest, StatusOrder.WAITINGPAYMENT, authorization))
                .thenThrow(new RuntimeException("Erro ao processar pagamento!"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> checkoutController.createCheckout(checkoutRequest, authorization));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Erro ao processar pagamento!", exception.getReason());
    }

    @Test
    void testUpdateCheckoutStatus_Success() {
        int orderId = 1;
        StatusOrder newStatus = StatusOrder.APPROVEDPAYMENT;

        CheckoutResponse expectedResponse = new CheckoutResponse();
        expectedResponse.setId(orderId);
        expectedResponse.setPaymentStatus(newStatus);

        when(checkoutUseCases.updateCheckoutStatus(orderId, newStatus, authorization))
                .thenReturn(expectedResponse);

        ResponseEntity<CheckoutResponse> response = checkoutController.updateCheckoutStatus(orderId, newStatus, authorization);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void testUpdateCheckoutStatus_BadRequest() {
        int orderId = 1;
        StatusOrder newStatus = StatusOrder.REJECTEDPAYMENT;

        when(checkoutUseCases.updateCheckoutStatus(orderId, newStatus, authorization))
                .thenThrow(new IllegalArgumentException("Status inválido!"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> checkoutController.updateCheckoutStatus(orderId, newStatus, authorization));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Status inválido!", exception.getReason());
    }

    @Test
    void testGetCheckoutByOrderId_Success() {
        int orderId = 1;
        CheckoutResponse expectedResponse = new CheckoutResponse();
        expectedResponse.setId(orderId);
        expectedResponse.setPaymentStatus(StatusOrder.WAITINGPAYMENT);

        when(checkoutUseCases.getCheckoutByOrderId(orderId)).thenReturn(expectedResponse);

        ResponseEntity<CheckoutResponse> response = checkoutController.getCheckoutByOrderId(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(orderId, response.getBody().getId());
    }

    @Test
    void testGetCheckoutByOrderId_NotFound() {
        int orderId = 1;

        when(checkoutUseCases.getCheckoutByOrderId(orderId)).thenThrow(new RuntimeException("Checkout não encontrado!"));

        ResponseEntity<CheckoutResponse> response = checkoutController.getCheckoutByOrderId(orderId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}


