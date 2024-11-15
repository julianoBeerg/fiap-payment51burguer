package com.fiap.burguer.infraestructure.repository;
import com.fiap.burguer.core.domain.CheckOut;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckOutRepository extends MongoRepository<CheckOut, String> {
    CheckOut findById(int id);
}