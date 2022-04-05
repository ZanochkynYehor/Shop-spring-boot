package com.example.shopspringboot.domain;

public enum UserRoles {
    ROLE_ADMIN, ROLE_USER;

    public static String getRole(int statusId) {
        return UserRoles.values()[statusId - 1].name();
    }
}