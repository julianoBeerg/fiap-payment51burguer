package com.fiap.burguer.core.application.ports;

import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.domain.CheckOut;

public interface IPaymentGateway {
    boolean processPayment(CheckOut checkOut);
}