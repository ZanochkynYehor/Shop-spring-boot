package com.example.shopspringboot.util;

import com.example.shopspringboot.domain.User;

import java.time.Duration;
import java.time.LocalDateTime;

public class UserUtil {

	public static boolean isBanned(User user) {
		return user.getBanDate() != null && user.getBanDate().toLocalDateTime().isAfter(LocalDateTime.now());
	}

	public static boolean isPasswordValid(User user, String password) {
		return user.getPassword().equals(password);
	}

	public static String banExpiresIn(User user) {
		long secondsToUnban = Duration.between(LocalDateTime.now(), user.getBanDate().toLocalDateTime()).toSeconds();
		return String.format("%d:%02d:%02d", secondsToUnban / 3600, (secondsToUnban % 3600) / 60,
				(secondsToUnban % 60));
	}
}