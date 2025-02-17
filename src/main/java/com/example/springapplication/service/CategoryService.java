package com.example.springapplication.service;

import java.util.List;

import com.example.springapplication.model.Category;

public interface CategoryService {
  List<Category> findAll();

  Category findById(Long id);

  Category save(Category category);

  Category update(Long id, Category category);

  void deleteById(Long id);
}
