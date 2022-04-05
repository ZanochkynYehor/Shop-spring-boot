package com.example.shopspringboot.service.impl;

import com.example.shopspringboot.domain.User;
import com.example.shopspringboot.repository.UserRepository;
import com.example.shopspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final int banDuration;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           @Value("${user_ban_duration}") int banDuration) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.banDuration = banDuration;
    }

    @Override
    public User createUser(User user) {
        user.setRoleId(2);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User getUser(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public void increaseSignInCount(User user) {
        user.setFailedSignInCount(user.getFailedSignInCount() + 1);
        userRepository.increaseSignInCount(user.getId(), user.getFailedSignInCount());
    }

    @Override
    public void banUser(User user) {
        Timestamp banDate = Timestamp.valueOf(LocalDateTime.now().plusMinutes(banDuration));
        user.setBanDate(banDate);
        userRepository.banUser(user.getId(), banDate);

        ScheduledExecutorService scheduledExecutorService =
                Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.schedule(() -> {
            unbanUser(user);
            scheduledExecutorService.shutdown();
        }, banDuration, TimeUnit.MINUTES);
    }

    @Override
    public void unbanUser(User user) {
        userRepository.unbanUser(user.getId());
    }
}