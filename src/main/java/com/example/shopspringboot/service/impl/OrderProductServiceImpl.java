package com.example.shopspringboot.service.impl;

import com.example.shopspringboot.domain.OrderProduct;
import com.example.shopspringboot.repository.OrderProductRepository;
import com.example.shopspringboot.service.OrderProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderProductServiceImpl implements OrderProductService {
    private final OrderProductRepository orderProductRepository;

    public OrderProductServiceImpl(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

    @Override
    public void addOrderProducts(List<OrderProduct> orderProducts) {
        orderProductRepository.saveAll(orderProducts);
    }
}