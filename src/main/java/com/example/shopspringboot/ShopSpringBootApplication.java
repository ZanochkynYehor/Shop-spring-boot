package com.example.shopspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ShopSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopSpringBootApplication.class, args);
    }
}