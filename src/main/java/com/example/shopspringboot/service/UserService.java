package com.example.shopspringboot.service;

import com.example.shopspringboot.domain.User;

public interface UserService {
    User createUser(User user);

    User getUser(String email);

    void increaseSignInCount(User user);

    void banUser(User user);

    void unbanUser(User user);
}