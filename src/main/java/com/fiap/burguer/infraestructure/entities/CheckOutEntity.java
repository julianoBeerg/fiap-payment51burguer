package com.fiap.burguer.infraestructure.entities;

import com.fiap.burguer.core.application.enums.StatusOrder;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.LocalDateTime;


@Document(collection = "checkout_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckOutEntity {
    @Id
    private String id;

    @Field("order_id")
    private int orderId;

    @Field("client_id")
    private Integer clientId;

    @Field("date_created")
    private LocalDateTime dateCreated;

    @Field("cpf")
    private String cpf;

    @Field("status")
    private StatusOrder paymentStatus;

    @Field("total_price")
    private double totalPrice;


    public CheckOutEntity(int orderId, double totalPrice, StatusOrder paymentStatus, Integer clientId, String cpf, LocalDateTime  dateCreated) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.paymentStatus = paymentStatus;
        this.clientId = clientId;
        this.cpf = cpf;
        this.dateCreated = dateCreated;
    }
}

