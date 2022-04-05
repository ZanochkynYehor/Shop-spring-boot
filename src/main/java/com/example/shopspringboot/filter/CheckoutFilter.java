package com.example.shopspringboot.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CheckoutFilter extends GenericFilterBean {
    private static final Logger log = LoggerFactory.getLogger(CheckoutFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        if (req.getRequestURI().equals("/checkout") && (session.getAttribute("cartSize") == null ||
                (Integer) session.getAttribute("cartSize") == 0)) {
            log.warn("User trying to access checkout page, but cart is empty, redirecting to " +
                    "/products");
            resp.sendRedirect("/products");
            return;
        }

        chain.doFilter(request, response);
    }
}