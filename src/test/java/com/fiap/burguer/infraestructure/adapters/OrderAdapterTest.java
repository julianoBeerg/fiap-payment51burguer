package com.fiap.burguer.infraestructure.adapters;

import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.driver.dto.OrderResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderAdapterTest {

    private OrderAdapter orderAdapter;
    private RestTemplate restTemplate;
    private final String authorizationHeader = "Bearer test-token";

    @Value("${base.url-order}")
    private String urlOrder = "http://a30ac0df094954ae7a136461057e6f2c-487494363.us-east-1.elb.amazonaws.com";

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        orderAdapter = new OrderAdapter();
        restTemplate = mock(RestTemplate.class);

        Field restTemplateField = OrderAdapter.class.getDeclaredField("restTemplate");
        restTemplateField.setAccessible(true);
        restTemplateField.set(orderAdapter, restTemplate);
    }

    @Test
    void testGetOrderById_Success() {
        int orderId = 123;
        OrderResponse expectedResponse = new OrderResponse();
        expectedResponse.setOrderId(orderId);
        expectedResponse.setStatus("WAITINGPAYMENT");

        ResponseEntity<OrderResponse> responseEntity = new ResponseEntity<>(expectedResponse, HttpStatus.OK);

        when(restTemplate.exchange(
                eq(urlOrder + "/orders/" + orderId), // URL com o orderId
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(OrderResponse.class)
        )).thenReturn(responseEntity);

        OrderResponse actualResponse = orderAdapter.getOrderById(orderId, authorizationHeader);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getOrderId(), actualResponse.getOrderId());
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
    }

    @Test
    void testUpdateOrderStatus() {
        int orderId = 123;
        StatusOrder newStatus = StatusOrder.APPROVEDPAYMENT;

        when(restTemplate.exchange(
                eq(urlOrder + "/orders/" + orderId + "/status?newStatus=" + newStatus.name()),
                eq(HttpMethod.PUT),
                any(HttpEntity.class),
                eq(Void.class)
        )).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        orderAdapter.updateOrderStatus(orderId, newStatus, authorizationHeader);

        verify(restTemplate, times(1)).exchange(
                eq(urlOrder + "/orders/" + orderId + "/status?newStatus=" + newStatus.name()),
                eq(HttpMethod.PUT),
                any(HttpEntity.class),
                eq(Void.class)
        );
    }
}
