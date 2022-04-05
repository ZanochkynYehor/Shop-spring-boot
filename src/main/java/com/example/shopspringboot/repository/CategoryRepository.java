package com.example.shopspringboot.repository;

import com.example.shopspringboot.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}