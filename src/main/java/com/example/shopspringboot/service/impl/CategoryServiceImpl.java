package com.example.shopspringboot.service.impl;

import com.example.shopspringboot.domain.Category;
import com.example.shopspringboot.repository.CategoryRepository;
import com.example.shopspringboot.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(
            CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
}