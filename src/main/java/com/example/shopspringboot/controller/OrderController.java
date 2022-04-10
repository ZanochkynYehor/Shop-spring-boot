package com.example.shopspringboot.controller;

import com.example.shopspringboot.domain.Order;
import com.example.shopspringboot.domain.User;
import com.example.shopspringboot.service.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public String getUserOrders(Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        List<Order> orders = orderService.getUserOrders(user.getId());

        model.addAttribute("orders", orders);
        return "orders";
    }
}