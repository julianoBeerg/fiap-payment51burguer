package com.fiap.burguer.infraestructure.entities;

import com.fiap.burguer.core.application.enums.StatusOrder;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.Date;
import java.util.UUID;

@Document(collection = "checkout_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckOutEntity {

    @Id
    private String id;

    @Field("order_id")
    private int orderId;

    @Field("date_created")
    private Date dateCreated;

    @Field("payment_status")
    private StatusOrder paymentStatus;

    @Field("total_price")
    private double totalPrice;

    @Field("transact_id")
    private String transactId;

    public CheckOutEntity(int orderId, double totalPrice, StatusOrder paymentStatus) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.paymentStatus = paymentStatus;
        this.dateCreated = new Date();
        this.transactId = UUID.randomUUID().toString();
    }
}
