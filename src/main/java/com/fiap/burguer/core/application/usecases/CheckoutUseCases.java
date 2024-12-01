package com.fiap.burguer.core.application.usecases;

import com.fiap.burguer.core.application.exception.ImpossibleToCheckoutException;
import com.fiap.burguer.core.application.exception.ResourceNotFoundException;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.application.ports.AuthenticationPort;
import com.fiap.burguer.core.application.ports.IOrderPort;
import com.fiap.burguer.driver.dto.CheckoutRequest;
import com.fiap.burguer.driver.dto.CheckoutResponse;
import com.fiap.burguer.driver.dto.OrderResponse;
import com.fiap.burguer.driver.presenters.CheckoutPresenter;
import com.fiap.burguer.infraestructure.entities.CheckOutEntity;
import com.fiap.burguer.infraestructure.repository.CheckOutRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CheckoutUseCases {

    private final CheckOutRepository checkOutRepository;
    private final AuthenticationPort authenticationPort;
    private final IOrderPort iOrderPort;

    public CheckoutUseCases(CheckOutRepository checkOutRepository, AuthenticationPort authenticationPort, IOrderPort iOrderPort) {
        this.checkOutRepository = checkOutRepository;
        this.authenticationPort = authenticationPort;
        this.iOrderPort = iOrderPort;
    }

    public CheckoutResponse getCheckoutByOrderId(int orderId) {
        return checkOutRepository.findByOrderId(orderId)
                .map(CheckOutEntity::toDomain)
                .map(CheckoutPresenter::mapCheckoutToResponse)
                .orElseThrow(() -> new ImpossibleToCheckoutException("Checkout não encontrado!"));
    }

    public List<CheckoutResponse> searchCheckouts(Integer orderId, StatusOrder status, Integer clientId, String cpf, String authorizationHeader) {
        Set<CheckOutEntity> checkouts = new HashSet<>();

        clientId = clientId == null ? authenticationPort.getClientIdFromToken(authorizationHeader) : clientId;
        cpf = (cpf == null || cpf.isEmpty()) ? authenticationPort.getCpfFromToken(authorizationHeader) : cpf;

        if (orderId != null) {
            checkOutRepository.findByOrderId(orderId).ifPresent(checkouts::add);
        }
        if (status != null) {
            checkouts.addAll(checkOutRepository.findByPaymentStatus(status));
        }
        if (clientId != null) {
            checkouts.addAll(checkOutRepository.findByClientId(clientId));
        }
        if (cpf != null) {
            checkouts.addAll(checkOutRepository.findByCpf(cpf));
        }

        if (checkouts.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum checkout encontrado!");
        }

        return checkouts.stream()
                .map(CheckOutEntity::toDomain)
                .map(CheckoutPresenter::mapCheckoutToResponse)
                .toList();
    }

    public CheckoutResponse createCheckout(CheckoutRequest checkoutRequest, StatusOrder statusOrder, String authorizationHeader) {
        authenticationPort.validateAuthorizationHeader(authorizationHeader);

        Integer clientId = authenticationPort.getClientIdFromToken(authorizationHeader);
        String cpf = authenticationPort.getCpfFromToken(authorizationHeader).replaceAll("\\D", "");

        CheckOutEntity checkoutEntity = new CheckOutEntity(checkoutRequest.getOrderId(), checkoutRequest.getTotalPrice(), statusOrder, clientId, cpf, LocalDateTime.now());
        checkOutRepository.save(checkoutEntity);

        return CheckoutPresenter.mapCheckoutToResponse(checkoutEntity.toDomain());
    }

    public CheckoutResponse updateCheckoutStatus(int orderId, StatusOrder newStatus, String authorizationHeader) {
        authenticationPort.validateAuthorizationHeader(authorizationHeader);

        OrderResponse orderResponse = iOrderPort.getOrderById(orderId, authorizationHeader);
        if (orderResponse == null) {
            throw new ResourceNotFoundException("Pedido não encontrado!");
        }

        String currentStatus = orderResponse.getStatus();
        if (!currentStatus.equalsIgnoreCase(StatusOrder.WAITINGPAYMENT.toString()) &&
                !currentStatus.equalsIgnoreCase(StatusOrder.REJECTEDPAYMENT.toString()) &&
                !currentStatus.equalsIgnoreCase(StatusOrder.APPROVEDPAYMENT.toString())) {
            throw new ImpossibleToCheckoutException("O status atual do pedido não permite atualizações!");
        }

        CheckOutEntity checkout = checkOutRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado no MongoDB!"));


        if (checkout.getPaymentStatus() == StatusOrder.APPROVEDPAYMENT) {
            throw new ImpossibleToCheckoutException("Status já está como APPROVEDPAYMENT e não pode ser alterado!");
        }

        if (newStatus != StatusOrder.APPROVEDPAYMENT && newStatus != StatusOrder.REJECTEDPAYMENT) {
            throw new ResourceNotFoundException("Status inválido!");
        }

        checkout.setPaymentStatus(newStatus);

        checkOutRepository.save(checkout);

        iOrderPort.updateOrderStatus(orderId, newStatus, authorizationHeader);

        return CheckoutPresenter.mapCheckoutToResponse(checkout.toDomain());
    }


}
