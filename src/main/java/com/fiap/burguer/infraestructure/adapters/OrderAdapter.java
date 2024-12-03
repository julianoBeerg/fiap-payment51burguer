package com.fiap.burguer.infraestructure.adapters;

import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.application.exception.ResourceNotFoundException;
import com.fiap.burguer.core.application.ports.IOrderPort;
import com.fiap.burguer.driver.dto.OrderResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrderAdapter implements IOrderPort {
    protected final RestTemplate restTemplate = new RestTemplate();

    @Value("${base.url-order}")
    private String urlOrder;


    @Override
    public OrderResponse getOrderById(int orderId, String authorizationHeader) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorizationHeader);
            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<OrderResponse> response = restTemplate.exchange(
                     "http://a30ac0df094954ae7a136461057e6f2c-487494363.us-east-1.elb.amazonaws.com/orders/" + orderId,
                    HttpMethod.GET,
                    entity,
                    OrderResponse.class);

            return response.getBody();
    }

    @Override
    public void updateOrderStatus(int orderId, StatusOrder newStatus, String authorizationHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizationHeader);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        restTemplate.exchange(
                 "http://a30ac0df094954ae7a136461057e6f2c-487494363.us-east-1.elb.amazonaws.com/orders/" + orderId + "/status?newStatus=" + newStatus.name(),
                HttpMethod.PUT,
                entity,
                Void.class);

    }
}
