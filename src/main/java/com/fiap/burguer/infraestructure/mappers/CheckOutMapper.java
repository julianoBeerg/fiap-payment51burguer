package com.fiap.burguer.infraestructure.mappers;

import com.fiap.burguer.infraestructure.entities.CheckOutEntity;
import com.fiap.burguer.core.domain.CheckOut;
import org.springframework.beans.BeanUtils;

public class CheckOutMapper {
    public static CheckOutEntity toEntity(CheckOut checkOut) {
        if(checkOut == null) return null;
        CheckOutEntity checkoutEntity = new CheckOutEntity();
        BeanUtils.copyProperties(checkOut, checkoutEntity);
        return checkoutEntity;
    }

    public static CheckOut toDomain(CheckOutEntity checkoutEntity) {
        if(checkoutEntity == null) return null;
        CheckOut checkOut = new CheckOut();
        BeanUtils.copyProperties(checkoutEntity, checkOut);
        return checkOut;
    }
}
