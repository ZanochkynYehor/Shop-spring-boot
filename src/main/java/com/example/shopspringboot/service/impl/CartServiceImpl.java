package com.example.shopspringboot.service.impl;

import com.example.shopspringboot.domain.Product;
import com.example.shopspringboot.service.CartService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartServiceImpl implements CartService {
    private final Map<Product, Integer> cart;

    public CartServiceImpl() {
        cart = new HashMap<>();
    }

    @Override
    public void addProduct(Product product) {
        cart.put(product, 1);
    }

    @Override
    public void setProductCount(Product product, int count) {
        cart.put(product, count);
    }

    @Override
    public void removeProduct(Product product) {
        cart.remove(product);
    }

    @Override
    public int getCartTotalPrice() {
        return cart.entrySet().stream().mapToInt(x -> x.getKey().getPrice() * x.getValue()).sum();
    }

    @Override
    public int getCartSize() {
        return cart.size();
    }

    @Override
    public Map<Product, Integer> getCart() {
        return cart;
    }

    @Override
    public void clearCart() {
        cart.clear();
    }
}