package com.fiap.burguer.core.application.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.driver.dto.CheckoutRequest;
import com.fiap.burguer.driver.dto.CheckoutResponse;
import com.fiap.burguer.infraestructure.entities.CheckOutEntity;
import com.fiap.burguer.infraestructure.repository.CheckOutRepository;
import com.fiap.burguer.core.application.ports.AuthenticationPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import org.mockito.*;


class CheckoutUseCasesTest {

    @InjectMocks
    private CheckoutUseCases checkoutUseCases;

    @Mock
    private CheckOutRepository checkOutRepository;

    @Mock
    private AuthenticationPort authenticationPort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCheckoutByOrderId_success() {
        int orderId = 1;
        CheckOutEntity checkoutEntity = new CheckOutEntity(orderId, 100.0, StatusOrder.WAITINGPAYMENT, 123, "12345678901", LocalDateTime.now());
        when(checkOutRepository.findByOrderId(orderId)).thenReturn(java.util.Optional.of(checkoutEntity));

        CheckoutResponse response = checkoutUseCases.getCheckoutByOrderId(orderId);

        assertNotNull(response);
        assertEquals(orderId, response.getId());
        verify(checkOutRepository, times(1)).findByOrderId(orderId);
    }

    @Test
    void testGetCheckoutByOrderId_notFound() {
        int orderId = 1;
        when(checkOutRepository.findByOrderId(orderId)).thenReturn(java.util.Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            checkoutUseCases.getCheckoutByOrderId(orderId);
        });

        assertEquals("Checkout não encontrado!", exception.getMessage());
    }

    @Test
    void testCreateCheckout_success() {
        CheckoutRequest checkoutRequest = new CheckoutRequest(1, 0.1);


        String authorization = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjcGYiOiI3NzU4MjkzMDAwMiIsIm5hbWUiOiJNYXJpYSBOdW5lcyIsImlkIjoyLCJpc0FkbWluIjp0cnVlLCJleHAiOjE3MzI0Njc1ODAsImVtYWlsIjoibWFyaWFOdW5lc0BleGFtcGxlLmNvbSJ9.q_2DLSqswBncxJs3hbzFYVotfdiAl-shY6OI9Wq2Wug";
        when(checkOutRepository.findByOrderId(checkoutRequest.getOrderId())).thenReturn(java.util.Optional.empty());
        when(authenticationPort.getClientIdFromToken(authorization)).thenReturn(123);
        when(authenticationPort.getCpfFromToken(authorization)).thenReturn("12345678901");

        CheckoutResponse response = checkoutUseCases.createCheckout(checkoutRequest, StatusOrder.WAITINGPAYMENT, authorization);

        assertNotNull(response);
        assertEquals(checkoutRequest.getOrderId(), response.getId());
        verify(checkOutRepository, times(1)).save(any(CheckOutEntity.class));
    }

    @Test
    void testCreateCheckout_orderAlreadyExists() {
        CheckoutRequest checkoutRequest = new CheckoutRequest(1, 0.1);
        String authorization = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjcGYiOiI3NzU4MjkzMDAwMiIsIm5hbWUiOiJNYXJpYSBOdW5lcyIsImlkIjoyLCJpc0FkbWluIjp0cnVlLCJleHAiOjE3MzI0Njc1ODAsImVtYWlsIjoibWFyaWFOdW5lc0BleGFtcGxlLmNvbSJ9.q_2DLSqswBncxJs3hbzFYVotfdiAl-shY6OI9Wq2Wug";
        CheckOutEntity existingCheckout = new CheckOutEntity(checkoutRequest.getOrderId(), 100.0, StatusOrder.WAITINGPAYMENT, 123, "12345678901", LocalDateTime.now());
        when(checkOutRepository.findByOrderId(checkoutRequest.getOrderId())).thenReturn(java.util.Optional.of(existingCheckout));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            checkoutUseCases.createCheckout(checkoutRequest, StatusOrder.WAITINGPAYMENT, authorization);
        });

        assertEquals("Pedido já existe com o ID informado!", exception.getMessage());
    }

    @Test
    void testUpdateCheckoutStatus_success() {
        int orderId = 1;
        StatusOrder newStatus = StatusOrder.APPROVEDPAYMENT;
        String authorization = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjcGYiOiI3NzU4MjkzMDAwMiIsIm5hbWUiOiJNYXJpYSBOdW5lcyIsImlkIjoyLCJpc0FkbWluIjp0cnVlLCJleHAiOjE3MzI0Njc1ODAsImVtYWlsIjoibWFyaWFOdW5lc0BleGFtcGxlLmNvbSJ9.q_2DLSqswBncxJs3hbzFYVotfdiAl-shY6OI9Wq2Wug";
        CheckOutEntity checkoutEntity = new CheckOutEntity(orderId, 100.0, StatusOrder.WAITINGPAYMENT, 123, "12345678901", LocalDateTime.now());
        when(checkOutRepository.findByOrderId(orderId)).thenReturn(java.util.Optional.of(checkoutEntity));

        CheckoutResponse response = checkoutUseCases.updateCheckoutStatus(orderId, newStatus, authorization);

        assertNotNull(response);
        assertEquals(newStatus, response.getPaymentStatus());
        verify(checkOutRepository, times(1)).save(checkoutEntity);
    }

    @Test
    void testUpdateCheckoutStatus_orderNotFound() {
        int orderId = 1;
        StatusOrder newStatus = StatusOrder.APPROVEDPAYMENT;
        String authorization = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjcGYiOiI3NzU4MjkzMDAwMiIsIm5hbWUiOiJNYXJpYSBOdW5lcyIsImlkIjoyLCJpc0FkbWluIjp0cnVlLCJleHAiOjE3MzI0Njc1ODAsImVtYWlsIjoibWFyaWFOdW5lc0BleGFtcGxlLmNvbSJ9.q_2DLSqswBncxJs3hbzFYVotfdiAl-shY6OI9Wq2Wug";
        when(checkOutRepository.findByOrderId(orderId)).thenReturn(java.util.Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            checkoutUseCases.updateCheckoutStatus(orderId, newStatus, authorization);
        });

        assertEquals("Pedido não encontrado!", exception.getMessage());
    }

    @Test
    void testUpdateCheckoutStatus_invalidStatus() {
        int orderId = 1;
        StatusOrder newStatus = StatusOrder.WAITINGPAYMENT;
        String authorization = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjcGYiOiI3NzU4MjkzMDAwMiIsIm5hbWUiOiJNYXJpYSBOdW5lcyIsImlkIjoyLCJpc0FkbWluIjp0cnVlLCJleHAiOjE3MzI0Njc1ODAsImVtYWlsIjoibWFyaWFOdW5lc0BleGFtcGxlLmNvbSJ9.q_2DLSqswBncxJs3hbzFYVotfdiAl-shY6OI9Wq2Wug";
        CheckOutEntity checkoutEntity = new CheckOutEntity(orderId, 100.0, StatusOrder.WAITINGPAYMENT, 123, "12345678901", LocalDateTime.now());
        when(checkOutRepository.findByOrderId(orderId)).thenReturn(java.util.Optional.of(checkoutEntity));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            checkoutUseCases.updateCheckoutStatus(orderId, newStatus, authorization);
        });

        assertEquals("Status inválido!", exception.getMessage());
    }

    @Test
    void testUpdateCheckoutStatus_paymentAlreadyApproved() {
        int orderId = 1;
        StatusOrder newStatus = StatusOrder.REJECTEDPAYMENT;
        String authorization = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjcGYiOiI3NzU4MjkzMDAwMiIsIm5hbWUiOiJNYXJpYSBOdW5lcyIsImlkIjoyLCJpc0FkbWluIjp0cnVlLCJleHAiOjE3MzI0Njc1ODAsImVtYWlsIjoibWFyaWFOdW5lc0BleGFtcGxlLmNvbSJ9.q_2DLSqswBncxJs3hbzFYVotfdiAl-shY6OI9Wq2Wug";

        CheckOutEntity checkoutEntity = new CheckOutEntity(orderId, 100.0, StatusOrder.APPROVEDPAYMENT, 123, "12345678901", LocalDateTime.now());
        when(checkOutRepository.findByOrderId(orderId)).thenReturn(java.util.Optional.of(checkoutEntity));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            checkoutUseCases.updateCheckoutStatus(orderId, newStatus, authorization);
        });

        assertEquals("Status já está como APPROVEDPAYMENT e não pode ser alterado!", exception.getMessage());
    }

    @Test
    void testSearchCheckouts_noResults() {
        when(checkOutRepository.findByOrderId(anyInt())).thenReturn(java.util.Optional.empty());
        when(checkOutRepository.findByPaymentStatus(any(StatusOrder.class))).thenReturn(List.of());
        when(checkOutRepository.findByClientId(anyInt())).thenReturn(List.of());
        when(checkOutRepository.findByCpf(anyString())).thenReturn(List.of());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            checkoutUseCases.searchCheckouts(1, StatusOrder.WAITINGPAYMENT, 123, "12345678901", "Bearer token");
        });

        assertEquals("Nenhum checkout encontrado!", exception.getMessage());
    }

    @Test
    void testSearchCheckouts_withFilters() {
        int orderId = 1;
        StatusOrder status = StatusOrder.WAITINGPAYMENT;
        Integer clientId = 123;
        String cpf = "12345678901";
        String authorization = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjcGYiOiI3NzU4MjkzMDAwMiIsIm5hbWUiOiJNYXJpYSBOdW5lcyIsImlkIjoyLCJpc0FkbWluIjp0cnVlLCJleHAiOjE3MzI0Njc1ODAsImVtYWlsIjoibWFyaWFOdW5lc0BleGFtcGxlLmNvbSJ9.q_2DLSqswBncxJs3hbzFYVotfdiAl-shY6OI9Wq2Wug";

        CheckOutEntity checkoutEntity = new CheckOutEntity(orderId, 100.0, status, clientId, cpf, LocalDateTime.now());
        when(checkOutRepository.findByOrderId(orderId)).thenReturn(java.util.Optional.of(checkoutEntity));
        when(checkOutRepository.findByPaymentStatus(status)).thenReturn(List.of(checkoutEntity));
        when(checkOutRepository.findByClientId(clientId)).thenReturn(List.of(checkoutEntity));
        when(checkOutRepository.findByCpf(cpf)).thenReturn(List.of(checkoutEntity));

        List<CheckoutResponse> responses = checkoutUseCases.searchCheckouts(orderId, status, clientId, cpf, authorization);

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(orderId, responses.getFirst().getId());
        assertEquals(status, responses.getFirst().getPaymentStatus());
    }


}
