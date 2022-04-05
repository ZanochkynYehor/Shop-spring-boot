package com.example.shopspringboot.repository;

import com.example.shopspringboot.domain.OrderProduct;
import com.example.shopspringboot.domain.OrderProductPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductPK> {
}