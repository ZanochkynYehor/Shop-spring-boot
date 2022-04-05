package com.example.shopspringboot.service;

import com.example.shopspringboot.domain.Category;
import com.example.shopspringboot.domain.Manufacturer;
import com.example.shopspringboot.domain.Product;
import com.example.shopspringboot.service.impl.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class CartServiceImplTest {
    private static final Product PRODUCT1 =
            new Product(1, "1", 1, "1", new Category(1, "1"), new Manufacturer(1, "1"));
    private static final Product PRODUCT2 =
            new Product(2, "2", 2, "2", new Category(2, "2"), new Manufacturer(2, "2"));

    @Autowired
    private CartService cartService;

    @BeforeEach
    public void before() {
        cartService.addProduct(PRODUCT1);
        cartService.addProduct(PRODUCT2);
        cartService.addProduct(PRODUCT2);
    }

    @Test
    void shouldAddProductsToCart() {
        assertEquals(2, cartService.getCartSize());
    }

    @Test
    void shouldRemoveProductsFromCart() {
        assertEquals(2, cartService.getCartSize());

        cartService.removeProduct(PRODUCT2);
        assertEquals(2, cartService.getCartSize());

        cartService.removeProduct(PRODUCT2);
        assertEquals(1, cartService.getCartSize());

        cartService.removeProduct(PRODUCT1);
        assertEquals(0, cartService.getCartSize());
    }

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
        @Bean
        public CartService cartService() {
            return new CartServiceImpl();
        }
    }
}