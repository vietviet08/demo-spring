package com.example.springapplication.service;

import java.util.List;

import com.example.springapplication.dto.request.CategoryRequest;
import com.example.springapplication.dto.response.CategoryResponse;
import com.example.springapplication.model.Category;

public interface CategoryService {
  List<Category> findAll();

  Category findById(Long id);

  CategoryResponse save(CategoryRequest categoryRequest);

  Category update(Long id, CategoryRequest categoryRequest);

  void deleteById(Long id);
}
