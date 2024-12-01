package com.fiap.burguer.core.application.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fiap.burguer.utils.TestTokenUtil;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.driver.dto.CheckoutRequest;
import com.fiap.burguer.driver.dto.CheckoutResponse;
import com.fiap.burguer.infraestructure.entities.CheckOutEntity;
import com.fiap.burguer.infraestructure.repository.CheckOutRepository;
import com.fiap.burguer.core.application.ports.AuthenticationPort;
import com.fiap.burguer.core.application.ports.IOrderPort;
import com.fiap.burguer.driver.dto.OrderResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class CheckoutUseCasesTest {

    @InjectMocks
    private CheckoutUseCases checkoutUseCases;

    @Mock
    private CheckOutRepository checkOutRepository;

    @Mock
    private AuthenticationPort authenticationPort;

    @Mock
    private IOrderPort iOrderPort;

    private String authorization;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authorization = TestTokenUtil.generateValidMockToken(Map.of(
                "cpf", "12345678901",
                "name", "Mock User",
                "id", 123,
                "isAdmin", true,
                "exp", 1893456000,
                "email", "mock@user.com"
        ));
    }



    @Test
    void testGetCheckoutByOrderId_success() {
        int orderId = 1;
        CheckOutEntity checkoutEntity = new CheckOutEntity(orderId, 100.0, StatusOrder.WAITINGPAYMENT, 123, "12345678901", LocalDateTime.now());
        when(checkOutRepository.findByOrderId(orderId)).thenReturn(Optional.of(checkoutEntity));

        CheckoutResponse response = checkoutUseCases.getCheckoutByOrderId(orderId);

        assertNotNull(response);
        assertEquals(orderId, response.getId());
        verify(checkOutRepository, times(1)).findByOrderId(orderId);
    }

    @Test
    void testGetCheckoutByOrderId_notFound() {
        int orderId = 1;
        when(checkOutRepository.findByOrderId(orderId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            checkoutUseCases.getCheckoutByOrderId(orderId);
        });

        assertEquals("Checkout não encontrado!", exception.getMessage());
    }

    @Test
    void testCreateCheckout_success() {
        CheckoutRequest checkoutRequest = new CheckoutRequest(1, 0.1);
        when(checkOutRepository.findByOrderId(checkoutRequest.getOrderId())).thenReturn(Optional.empty());
        when(authenticationPort.getClientIdFromToken(authorization)).thenReturn(123);
        when(authenticationPort.getCpfFromToken(authorization)).thenReturn("12345678901");
        OrderResponse orderResponse = mock(OrderResponse.class);
        when(iOrderPort.getOrderById(checkoutRequest.getOrderId(), authorization)).thenReturn(orderResponse);

        CheckoutResponse response = checkoutUseCases.createCheckout(checkoutRequest, StatusOrder.WAITINGPAYMENT, authorization);

        assertNotNull(response);
        assertEquals(checkoutRequest.getOrderId(), response.getId());
        verify(checkOutRepository, times(1)).save(any(CheckOutEntity.class));
    }

    @Test
    void testUpdateCheckoutStatus_orderNotFound() {
        int orderId = 1;
        StatusOrder newStatus = StatusOrder.APPROVEDPAYMENT;
        when(checkOutRepository.findByOrderId(orderId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            checkoutUseCases.updateCheckoutStatus(orderId, newStatus, authorization);
        });

        assertEquals("Pedido não encontrado!", exception.getMessage());
    }

    @Test
    void testSearchCheckouts_noResults() {
        when(checkOutRepository.findByOrderId(anyInt())).thenReturn(Optional.empty());
        when(checkOutRepository.findByPaymentStatus(any(StatusOrder.class))).thenReturn(List.of());
        when(checkOutRepository.findByClientId(anyInt())).thenReturn(List.of());
        when(checkOutRepository.findByCpf(anyString())).thenReturn(List.of());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            checkoutUseCases.searchCheckouts(1, StatusOrder.WAITINGPAYMENT, 123, "12345678901", authorization);
        });

        assertEquals("Nenhum checkout encontrado!", exception.getMessage());
    }

    @Test
    void testSearchCheckouts_withFilters() {
        int orderId = 1;
        StatusOrder status = StatusOrder.WAITINGPAYMENT;
        Integer clientId = 123;
        String cpf = "12345678901";

        CheckOutEntity checkoutEntity = new CheckOutEntity(orderId, 100.0, status, clientId, cpf, LocalDateTime.now());
        when(checkOutRepository.findByOrderId(orderId)).thenReturn(Optional.of(checkoutEntity));
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


