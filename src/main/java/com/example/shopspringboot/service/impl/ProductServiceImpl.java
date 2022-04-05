package com.example.shopspringboot.service.impl;

import com.example.shopspringboot.domain.Product;
import com.example.shopspringboot.repository.ProductRepository;
import com.example.shopspringboot.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository repository) {
        productRepository = repository;
    }

    @Override
    public Product getProduct(int id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Product> getProducts(Specification<Product> spec, Pageable pageable) {
        return productRepository.findAll(spec, pageable);
    }
}