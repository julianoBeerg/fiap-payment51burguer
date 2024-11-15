package com.fiap.burguer.infraestructure.adapters;

import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.application.ports.IPaymentGateway;
import com.fiap.burguer.core.domain.CheckOut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PaymentGateway implements IPaymentGateway {

    private static final String MERCADO_LIVRE_API_URL = "https://api.mercadolivre.com/payments";

    public boolean processPayment(CheckOut checkOut) {
        boolean paymentApproved = callMercadoLivreApi(checkOut);
        if (paymentApproved) {
            checkOut.setPaymentStatus(StatusOrder.APPROVEDPAYMENT);
        } else {
            checkOut.setPaymentStatus(StatusOrder.REJECTEDPAYMENT);
        }
        return paymentApproved;
    }

    private boolean callMercadoLivreApi(CheckOut checkOut) {
        RestTemplate restTemplate = new RestTemplate();

        String paymentData = preparePaymentData(checkOut);

        String response = restTemplate.postForObject(MERCADO_LIVRE_API_URL, paymentData, String.class);

        return response != null && response.contains("approved");
    }

    private String preparePaymentData(CheckOut checkOut) {
        return "{ \"amount\": \"" + checkOut.getTotalPrice() + "\", \"payment_method\": \"credit_card\" }";
    }
}