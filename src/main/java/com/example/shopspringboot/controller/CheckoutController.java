package com.example.shopspringboot.controller;

import com.example.shopspringboot.domain.Order;
import com.example.shopspringboot.domain.OrderProduct;
import com.example.shopspringboot.domain.Product;
import com.example.shopspringboot.domain.User;
import com.example.shopspringboot.service.CartService;
import com.example.shopspringboot.service.OrderProductService;
import com.example.shopspringboot.service.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class CheckoutController {
    private final CartService cartService;
    private final OrderService orderService;
    private final OrderProductService orderProductService;

    public CheckoutController(CartService cartService, OrderService orderService,
                              OrderProductService orderProductService) {
        this.cartService = cartService;
        this.orderService = orderService;
        this.orderProductService = orderProductService;
    }

    @GetMapping("/checkout")
    public String getCheckoutPage() {
        return "checkout";
    }

    @PostMapping("/checkout")
    public String doPost(HttpServletRequest req, Authentication auth) {
        User user = (User) auth.getPrincipal();

        Order order = orderService.createOrder(user.getId());
        List<OrderProduct> orderedProducts = getOrderedProducts(order, cartService.getCart());
        orderProductService.addOrderProducts(orderedProducts);

        cartService.clearCart();
        req.getSession().setAttribute("cartSize", 0);

        return "redirect:/products";
    }

    private List<OrderProduct> getOrderedProducts(Order order, Map<Product, Integer> cart) {
        List<OrderProduct> orderedProducts = new ArrayList<>();
        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            orderedProducts.add(new OrderProduct(order, entry.getKey(), entry.getValue(),
                    entry.getKey().getPrice()));
        }
        return orderedProducts;
    }
}