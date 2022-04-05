package com.example.shopspringboot.controller;

import com.example.shopspringboot.domain.Product;
import com.example.shopspringboot.service.CartService;
import com.example.shopspringboot.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {
    public static final String CART_SIZE = "cartSize";
    public static final String CART_TOTAL_PRICE = "cartTotalPrice";

    private final CartService cartService;
    private final ProductService productService;

    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping
    public Map<String, Object> getCart() {
        return cartToJson();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> addProductToCart(@RequestParam String productId,
                                                HttpServletRequest req) {
        Product product = productService.getProduct(Integer.parseInt(productId));
        cartService.addProduct(product);
        int cartSize = cartService.getCartSize();

        req.getSession().setAttribute(CART_SIZE, cartSize);
        Map<String, Object> result = new HashMap<>();
        result.put(CART_SIZE, cartSize);
        return result;
    }

    @PutMapping
    public Map<String, Object> changeProd2uctCount(@RequestParam String productId,
                                                  @RequestParam String productCount) {
        Product product = productService.getProduct(Integer.parseInt(productId));
        cartService.setProductCount(product, Integer.parseInt(productCount));

        Map<String, Object> result = new HashMap<>();
        result.put(CART_TOTAL_PRICE, cartService.getCartTotalPrice());
        return result;
    }

    @DeleteMapping
    public Map<String, Object> deleteProductOrClearCart(
            @RequestParam(required = false) String productId, HttpServletRequest req) {
        if (productId != null) {
            Product product = productService.getProduct(Integer.parseInt(productId));
            cartService.removeProduct(product);
        } else {
            cartService.clearCart();
        }

        req.getSession().setAttribute(CART_SIZE, cartService.getCartSize());

        Map<String, Object> result = new HashMap<>();
        result.put(CART_SIZE, cartService.getCartSize());
        result.put(CART_TOTAL_PRICE, cartService.getCartTotalPrice());
        return result;
    }

    private Map<String, Object> cartToJson() {
        Map<String, Object> cart = new HashMap<>();
        for (Map.Entry<Product, Integer> entry : cartService.getCart().entrySet()) {
            int id = entry.getKey().getId();
            String name = entry.getKey().getName();
            int price = entry.getKey().getPrice();
            int count = entry.getValue();

            Map<String, Object> value = new HashMap<>();
            value.put("name", name);
            value.put("price", price);
            value.put("count", count);
            cart.put(String.valueOf(id), value);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("cart", cart);
        result.put(CART_SIZE, cartService.getCartSize());
        result.put(CART_TOTAL_PRICE, cartService.getCartTotalPrice());
        return result;
    }
}