package com.example.springapplication.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.springapplication.model.Category;
import com.example.springapplication.repository.CategoryRepository;
import com.example.springapplication.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  @Override
  public List<Category> findAll() {
    return categoryRepository.findAll();
  }

  @Override
  public Category findById(Long id) {
    return categoryRepository.findById(id).orElse(null);
  }

  @Override
  public Category save(Category category) {
    return categoryRepository.save(category);
  }

  @Override
  public Category update(Long id, Category category) {
    Category updatedCategory = categoryRepository.findById(id).orElse(null);
    updatedCategory.setName(category.getName());
    updatedCategory.setDescription(category.getDescription());
    return categoryRepository.save(updatedCategory);
  }

  @Override
  public void deleteById(Long id) {
    categoryRepository.deleteById(id);
  }

}
