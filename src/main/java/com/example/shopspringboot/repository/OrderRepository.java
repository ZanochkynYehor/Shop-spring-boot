package com.example.shopspringboot.repository;

import com.example.shopspringboot.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findOrdersByUserId(int userId);
}