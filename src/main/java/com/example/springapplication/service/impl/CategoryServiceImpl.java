package com.example.springapplication.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.springapplication.dto.request.CategoryRequest;
import com.example.springapplication.dto.response.CategoryResponse;
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
  public CategoryResponse save(CategoryRequest categoryRequest) {
    Category category = Category.builder()
        .name(categoryRequest.name())
        .description(categoryRequest.description())
        .build();
    
    Category savedCategory = categoryRepository.save(category);
    
    return new CategoryResponse(
        savedCategory.getId(),
        savedCategory.getName(),
        savedCategory.getDescription()
    );
  }

  @Override
  public Category update(Long id, CategoryRequest categoryRequest) {
    Category updatedCategory = categoryRepository.findById(id).orElse(null);
    if(updatedCategory == null) {
      return null;
    }
    updatedCategory.setName(categoryRequest.name());
    updatedCategory.setDescription(categoryRequest.description());
    return categoryRepository.save(updatedCategory);
  }

  @Override
  public void deleteById(Long id) {
    categoryRepository.deleteById(id);
  }

}
