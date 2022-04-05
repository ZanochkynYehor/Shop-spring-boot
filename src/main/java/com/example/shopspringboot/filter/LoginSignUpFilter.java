package com.example.shopspringboot.filter;

import com.example.shopspringboot.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginSignUpFilter extends GenericFilterBean {
    private static final Logger log = LoggerFactory.getLogger(LoginSignUpFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            HttpServletRequest req = (HttpServletRequest) request;
            User user =
                    (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (req.getRequestURI().equals("/login")) {
                log.warn("User with id {} is authenticated, but trying to access login page, " +
                        "redirecting to /products", user.getId());
                ((HttpServletResponse) response).sendRedirect("/products");
            } else if (req.getRequestURI().startsWith("/signup")) {
                log.warn("User with id {} is authenticated, but trying to access signup page, " +
                        "redirecting to /products", user.getId());
                ((HttpServletResponse) response).sendRedirect("/products");
            }
        }
        chain.doFilter(request, response);
    }
}