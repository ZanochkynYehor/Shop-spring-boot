package com.example.shopspringboot.service.impl;

import com.example.shopspringboot.domain.Order;
import com.example.shopspringboot.repository.OrderRepository;
import com.example.shopspringboot.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(int userId) {
        Order order = new Order();
        order.setUserId(userId);
        return orderRepository.save(order);
    }

    @Transactional
    @Override
    public List<Order> getUserOrders(int userId) {
        return orderRepository.findOrdersByUserId(userId);
    }
}