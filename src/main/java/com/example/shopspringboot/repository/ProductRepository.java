package com.example.shopspringboot.repository;

import com.example.shopspringboot.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository extends JpaRepository<Product, Integer>,
        JpaSpecificationExecutor<Product> {

}