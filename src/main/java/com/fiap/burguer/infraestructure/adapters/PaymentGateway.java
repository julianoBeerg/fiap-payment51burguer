package com.fiap.burguer.infraestructure.adapters;

import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.application.ports.IPaymentGateway;
import com.fiap.burguer.core.domain.CheckOut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PaymentGateway implements IPaymentGateway {

    public boolean processPayment(CheckOut checkOut) {
        boolean paymentApproved = simulatePaymentResult();
        if (paymentApproved) {
            checkOut.setPaymentStatus(StatusOrder.APPROVEDPAYMENT);
        } else {
            checkOut.setPaymentStatus(StatusOrder.REJECTEDPAYMENT);
        }
        return paymentApproved;
    }

    private boolean simulatePaymentResult() {
        return Math.random() > 0.5;
    }
}
