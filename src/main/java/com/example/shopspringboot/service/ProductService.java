package com.example.shopspringboot.service;

import com.example.shopspringboot.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface ProductService {
    Product getProduct(int id);

    Page<Product> getProducts(Specification<Product> spec, Pageable pageable);
}