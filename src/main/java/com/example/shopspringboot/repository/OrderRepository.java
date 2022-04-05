package com.example.shopspringboot.repository;

import com.example.shopspringboot.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}