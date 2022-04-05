package com.example.shopspringboot.service;

import com.example.shopspringboot.domain.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getCategories();
}