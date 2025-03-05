package com.example.springapplication.service;

import java.util.List;

import com.example.springapplication.model.Category;
import com.example.springapplication.model.CategoryResponse;

public interface CategoryService {
  List<Category> findAll();

  Category findById(Long id);

  CategoryResponse save(Category category);

  Category update(Long id, Category category);

  void deleteById(Long id);
}
