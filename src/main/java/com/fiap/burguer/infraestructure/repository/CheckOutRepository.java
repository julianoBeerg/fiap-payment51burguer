package com.fiap.burguer.infraestructure.repository;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.infraestructure.entities.CheckOutEntity;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Hidden
public interface CheckOutRepository extends MongoRepository<CheckOutEntity, String> {

    Optional<CheckOutEntity> findById(String id);
    Optional<CheckOutEntity> findByOrderId(int orderId);
    List<CheckOutEntity> findByPaymentStatus(StatusOrder paymentStatus);
    List<CheckOutEntity> findByClientId(Integer clientId);
    List<CheckOutEntity> findByCpf(String cpf);
}