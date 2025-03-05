package com.example.springapplication.service;

import com.example.springapplication.model.Category;
import com.example.springapplication.model.CategoryResponse;
import com.example.springapplication.repository.CategoryRepository;
import com.example.springapplication.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTests {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category1;
    private Category category2;

    @BeforeEach
    void setUp() {
        category1 = new Category(1L, "Category 1", "Description 1");
        category2 = new Category(2L, "Category 2", "Description 2");
    }

    @Test
    void findAll_shouldReturnListOfCategories() {
        List<Category> categories = Arrays.asList(category1, category2);
        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.findAll();

        assertEquals(2, result.size());
        assertEquals(category1, result.get(0));
        assertEquals(category2, result.get(1));
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void findById_shouldReturnCategoryIfExists() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category1));

        Category result = categoryService.findById(1L);

        assertEquals(category1, result);
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void findById_shouldReturnNullIfNotExists() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        Category result = categoryService.findById(1L);

        assertNull(result);
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void save_shouldReturnSavedCategoryResponse() {
        Category categoryToSave = new Category(null, "New Category", "New Description");
        Category savedCategory = new Category(1L, "New Category", "New Description");
        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);

        CategoryResponse result = categoryService.save(categoryToSave);

        assertEquals(1L, result.getId());
        assertEquals("New Category", result.getName());
        assertEquals("New Description", result.getDecsription());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void update_shouldUpdateCategoryIfExists() {
        Category updatedCategoryData = new Category(null, "Updated Category", "Updated Description");
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category1));
        when(categoryRepository.save(any(Category.class))).thenReturn(category1);

        Category result = categoryService.update(1L, updatedCategoryData);

        assertEquals("Updated Category", result.getName());
        assertEquals("Updated Description", result.getDescription());
        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void update_shouldReturnNullIfNotExists() {
        Category updatedCategoryData = new Category(null, "Updated Category", "Updated Description");
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        Category result = categoryService.update(1L, updatedCategoryData);

        assertNull(result);
        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void deleteById_shouldDeleteCategory() {
        categoryService.deleteById(1L);

        verify(categoryRepository, times(1)).deleteById(1L);
    }
}