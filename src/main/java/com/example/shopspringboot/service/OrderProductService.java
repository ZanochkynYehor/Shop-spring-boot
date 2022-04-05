package com.example.shopspringboot.service;

import com.example.shopspringboot.domain.OrderProduct;

import java.util.List;

public interface OrderProductService {
    void addOrderProducts(List<OrderProduct> orderProducts);
}