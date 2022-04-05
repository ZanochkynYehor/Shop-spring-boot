package com.example.shopspringboot.service;

import com.example.shopspringboot.domain.Product;

import java.util.Map;

public interface CartService {

    void addProduct(Product product);

    void setProductCount(Product product, int count);

    void removeProduct(Product product);

    int getCartTotalPrice();

    int getCartSize();

    Map<Product, Integer> getCart();

    void clearCart();
}