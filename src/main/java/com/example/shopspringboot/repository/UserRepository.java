package com.example.shopspringboot.repository;

import com.example.shopspringboot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.failedSignInCount = :cnt WHERE u.id = :userId")
    void increaseSignInCount(@Param("userId") int userId, @Param("cnt") int cnt);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.banDate = :date WHERE u.id = :userId")
    void banUser(@Param("userId") int userId, @Param("date") Timestamp date);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.banDate = null, u.failedSignInCount = 0 WHERE u.id = :userId")
    void unbanUser(int userId);
}