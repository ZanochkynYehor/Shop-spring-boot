package com.example.shopspringboot.util;

import com.example.shopspringboot.domain.OrderStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<OrderStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(OrderStatus attribute) {
        return attribute.ordinal() + 1;
    }

    @Override
    public OrderStatus convertToEntityAttribute(Integer dbData) {
        return OrderStatus.values()[dbData - 1];
    }
}