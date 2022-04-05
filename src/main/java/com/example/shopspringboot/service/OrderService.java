package com.example.shopspringboot.service;

import com.example.shopspringboot.domain.Order;

public interface OrderService {
	Order createOrder(int userId);
}