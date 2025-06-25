package com.example.springapplication.controller;

import com.example.springapplication.dto.request.CategoryRequest;
import com.example.springapplication.dto.response.CategoryResponse;
import com.example.springapplication.model.Category;
import com.example.springapplication.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTests {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void findAll_shouldReturnListOfCategories() throws Exception {
        List<Category> categories = Arrays.asList(
                new Category(1L, "Category 1", "Description 1"),
                new Category(2L, "Category 2", "Description 2")
        );

        when(categoryService.findAll()).thenReturn(categories);

        mockMvc.perform(get("/category"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Category 1"))
                .andExpect(jsonPath("$[0].description").value("Description 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Category 2"))
                .andExpect(jsonPath("$[1].description").value("Description 2"));

        verify(categoryService, times(1)).findAll();
    }

    @Test
    void findById_shouldReturnCategory() throws Exception {
        Category category = new Category(1L, "Category 1", "Description 1");
        when(categoryService.findById(1L)).thenReturn(category);

        mockMvc.perform(get("/category/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Category 1"))
                .andExpect(jsonPath("$.description").value("Description 1"));

        verify(categoryService, times(1)).findById(1L);
    }

    @Test
    void save_shouldReturnCreatedCategoryResponse() throws Exception {
        CategoryRequest categoryRequest = new CategoryRequest("New Category", "New Description");
        CategoryResponse categoryResponse = new CategoryResponse(1L, "New Category", "New Description");

        when(categoryService.save(any(CategoryRequest.class))).thenReturn(categoryResponse);

        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New Category"))
                .andExpect(jsonPath("$.description").value("New Description"));

        verify(categoryService, times(1)).save(any(CategoryRequest.class));
    }

    @Test
    void update_shouldReturnUpdatedCategory() throws Exception {
        CategoryRequest categoryRequest = new CategoryRequest("Updated Category", "Updated Description");
        Category category = new Category(1L, "Updated Category", "Updated Description");
        when(categoryService.update(eq(1L), any(CategoryRequest.class))).thenReturn(category);

        mockMvc.perform(put("/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated Category"))
                .andExpect(jsonPath("$.description").value("Updated Description"));

        verify(categoryService, times(1)).update(eq(1L), any(CategoryRequest.class));
    }

    @Test
    void deleteById_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/category/1"))
                .andExpect(status().isNoContent());

        verify(categoryService, times(1)).deleteById(1L);
    }
}