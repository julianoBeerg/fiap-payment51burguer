package com.fiap.burguer.core.application.usecases;
import com.fiap.burguer.core.application.ports.IPaymentGateway;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.domain.CheckOut;
import com.fiap.burguer.infraestructure.repository.CheckOutRepository;
import org.springframework.stereotype.Service;

@Service
public class CheckoutUseCases {

    private final IPaymentGateway paymentGateway;
    private final CheckOutRepository checkOutRepository;

    public CheckoutUseCases(IPaymentGateway paymentGateway, CheckOutRepository checkOutRepository) {
        this.paymentGateway = paymentGateway;
        this.checkOutRepository = checkOutRepository;
    }

    public CheckOut createCheckout(int id, StatusOrder statusOrder) {
        CheckOut checkOut = new CheckOut();
        checkOut.setId(id);
        checkOut.setPaymentStatus(statusOrder);
        boolean paymentResult = paymentGateway.processPayment(checkOut);
        //todo jnunes
//        if (paymentResult) {
//            checkOutRepository.save(checkOut);
//        }
        return checkOut;
    }

    public CheckOut findById(int id) {
        return checkOutRepository.findById(id);
    }
}