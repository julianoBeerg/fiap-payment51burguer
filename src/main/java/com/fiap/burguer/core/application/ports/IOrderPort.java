package com.fiap.burguer.core.application.ports;

import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.driver.dto.OrderResponse;

public interface IOrderPort {
    OrderResponse getOrderById(int orderId, String authorizationHeader);
    void updateOrderStatus(int orderId, StatusOrder newStatus, String authorizationHeader);
}
