package com.fiap.burguer.driver.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.fiap.burguer.driver.dto.CheckoutRequest;
import com.fiap.burguer.driver.dto.ErrorResponse;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.application.usecases.CheckoutUseCases;
import com.fiap.burguer.driver.dto.CheckoutResponse;
import com.fiap.burguer.infraestructure.adapters.AuthenticationAdapter;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CheckoutControllerTest {

    @InjectMocks
    private CheckoutController checkoutController;

    @Mock
    private CheckoutUseCases checkoutUseCases;

    @Mock
    private AuthenticationAdapter authenticationAdapter;

    @Test
    public void testGetCheckoutByOrderId_Success() {
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
    void testSearchCheckouts_Success() {
        Integer orderId = 1;
        StatusOrder status = StatusOrder.WAITINGPAYMENT;
        Integer clientId = 123;
        String cpf = "12345678901";
        String authorization = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjcGYiOiI3NzU4MjkzMDAwMiIsIm5hbWUiOiJNYXJpYSBOdW5lcyIsImlkIjoyLCJpc0FkbWluIjp0cnVlLCJleHAiOjE3MzI0Njc1ODAsImVtYWlsIjoibWFyaWFOdW5lc0BleGFtcGxlLmNvbSJ9.q_2DLSqswBncxJs3hbzFYVotfdiAl-shY6OI9Wq2Wug";

        CheckoutResponse checkoutResponse = new CheckoutResponse();
        checkoutResponse.setId(orderId);
        checkoutResponse.setPaymentStatus(StatusOrder.WAITINGPAYMENT);
        List<CheckoutResponse> expectedResponses = List.of(checkoutResponse);

        when(checkoutUseCases.searchCheckouts(orderId, status, clientId, cpf, authorization))
                .thenReturn(expectedResponses);

        ResponseEntity<?> response = checkoutController.searchCheckouts(orderId, status, clientId, cpf, authorization);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertInstanceOf(List.class, response.getBody());
        List<CheckoutResponse> responseBody = (List<CheckoutResponse>) response.getBody();
        assertEquals(1, responseBody.size());
        assertEquals(orderId, responseBody.getFirst().getId());
    }

    @Test
    public void testGetCheckoutByOrderId_NotFound() {
        int orderId = 1;

        when(checkoutUseCases.getCheckoutByOrderId(orderId)).thenThrow(new RuntimeException("Checkout não encontrado!"));

        ResponseEntity<CheckoutResponse> response = checkoutController.getCheckoutByOrderId(orderId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateCheckout_Success() {
        CheckoutRequest checkoutRequest = new CheckoutRequest(1, 0.1);

        String authorization = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjcGYiOiI3NzU4MjkzMDAwMiIsIm5hbWUiOiJNYXJpYSBOdW5lcyIsImlkIjoyLCJpc0FkbWluIjp0cnVlLCJleHAiOjE3MzI0Njc1ODAsImVtYWlsIjoibWFyaWFOdW5lc0BleGFtcGxlLmNvbSJ9.q_2DLSqswBncxJs3hbzFYVotfdiAl-shY6OI9Wq2Wug";
        CheckoutResponse expectedResponse = new CheckoutResponse();
        expectedResponse.setId(checkoutRequest.getOrderId());

        when(checkoutUseCases.createCheckout(checkoutRequest, StatusOrder.WAITINGPAYMENT, authorization))
                .thenReturn(expectedResponse);

        ResponseEntity<?> response = checkoutController.createCheckout(checkoutRequest, authorization);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void testUpdateCheckoutStatus_Success() {
        int orderId = 1;
        StatusOrder newStatus = StatusOrder.APPROVEDPAYMENT;
        String authorization = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjcGYiOiI3NzU4MjkzMDAwMiIsIm5hbWUiOiJNYXJpYSBOdW5lcyIsImlkIjoyLCJpc0FkbWluIjp0cnVlLCJleHAiOjE3MzI0Njc1ODAsImVtYWlsIjoibWFyaWFOdW5lc0BleGFtcGxlLmNvbSJ9.q_2DLSqswBncxJs3hbzFYVotfdiAl-shY6OI9Wq2Wug";
        CheckoutResponse expectedResponse = new CheckoutResponse();
        expectedResponse.setId(orderId);
        expectedResponse.setPaymentStatus(newStatus);

        when(checkoutUseCases.updateCheckoutStatus(orderId, newStatus, authorization))
                .thenReturn(expectedResponse);

        ResponseEntity<?> response = checkoutController.updateCheckoutStatus(orderId, newStatus, authorization);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void testUpdateCheckoutStatus_BadRequest() {
        int orderId = 1;
        StatusOrder newStatus = StatusOrder.REJECTEDPAYMENT;
        String authorization = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjcGYiOiI3NzU4MjkzMDAwMiIsIm5hbWUiOiJNYXJpYSBOdW5lcyIsImlkIjoyLCJpc0FkbWluIjp0cnVlLCJleHAiOjE3MzI0Njc1ODAsImVtYWlsIjoibWFyaWFOdW5lc0BleGFtcGxlLmNvbSJ9.q_2DLSqswBncxJs3hbzFYVotfdiAl-shY6OI9Wq2Wug";

        when(checkoutUseCases.updateCheckoutStatus(orderId, newStatus, authorization))
                .thenThrow(new RuntimeException("Status inválido!"));

        ResponseEntity<?> response = checkoutController.updateCheckoutStatus(orderId, newStatus, authorization);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testSearchCheckouts_NotFound() {
        Integer orderId = 1;
        StatusOrder status = StatusOrder.WAITINGPAYMENT;
        Integer clientId = 123;
        String cpf = "12345678901";
        String authorization = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjcGYiOiI3NzU4MjkzMDAwMiIsIm5hbWUiOiJNYXJpYSBOdW5lcyIsImlkIjoyLCJpc0FkbWluIjp0cnVlLCJleHAiOjE3MzI0Njc1ODAsImVtYWlsIjoibWFyaWFOdW5lc0BleGFtcGxlLmNvbSJ9.q_2DLSqswBncxJs3hbzFYVotfdiAl-shY6OI9Wq2Wug";

        when(checkoutUseCases.searchCheckouts(orderId, status, clientId, cpf, authorization))
                .thenThrow(new RuntimeException("Nenhum checkout encontrado!"));

        ResponseEntity<?> response = checkoutController.searchCheckouts(orderId, status, clientId, cpf, authorization);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertInstanceOf(ErrorResponse.class, response.getBody());
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertEquals("Nenhum checkout encontrado!", errorResponse.getMessage());
    }
    @Test
    void testCreateCheckout_BadRequest() {
        CheckoutRequest checkoutRequest = new CheckoutRequest(1, 0.1);

        String authorization = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjcGYiOiI3NzU4MjkzMDAwMiIsIm5hbWUiOiJNYXJpYSBOdW5lcyIsImlkIjoyLCJpc0FkbWluIjp0cnVlLCJleHAiOjE3MzI0Njc1ODAsImVtYWlsIjoibWFyaWFOdW5lc0BleGFtcGxlLmNvbSJ9.q_2DLSqswBncxJs3hbzFYVotfdiAl-shY6OI9Wq2Wug";

        when(checkoutUseCases.createCheckout(checkoutRequest, StatusOrder.WAITINGPAYMENT, authorization))
                .thenThrow(new RuntimeException("Pedido já existe com o ID informado!"));

        ResponseEntity<?> response = checkoutController.createCheckout(checkoutRequest, authorization);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertInstanceOf(ErrorResponse.class, response.getBody());
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertEquals("Pedido já existe com o ID informado!", errorResponse.getMessage());
    }
}

