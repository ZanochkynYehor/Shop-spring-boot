package com.example.shopspringboot.service;

import com.example.shopspringboot.domain.Order;

import java.util.List;

public interface OrderService {
	Order createOrder(int userId);

	List<Order> getUserOrders(int userId);
}