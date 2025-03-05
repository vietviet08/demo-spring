package com.example.springapplication.repository.category;

import com.example.springapplication.model.Category;
import com.example.springapplication.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@Slf4j
@DataJpaTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryRepositoryTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Test save category")
    @Order(1)
    @Rollback(value = false)
    public void saveCategory() {
        Category category = Category.builder()
                .name("Laptop")
                .description("Laptop category")
                .build();
        categoryRepository.save(category);

        //Verify
        log.info("Saved category: {}", category);
        Assertions.assertNotNull(category.getId());
    }

    @Test
    @DisplayName("Test find category by id")
    @Order(2)
    public void findCategoryById() {
        Category category = Category.builder()
                .name("Laptop")
                .description("Laptop category")
                .build();
        categoryRepository.save(category);

        //Verify
        Category foundCategory = categoryRepository.findById(category.getId()).orElse(null);
        log.info("Found category: {}", foundCategory);
        Assertions.assertNotNull(foundCategory);
    }

    @Test
    @DisplayName("Test find all categories")
    @Order(3)
    public void findAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        log.info("Categories: {}", categories);
        Assertions.assertNotNull(categories);
    }

    @Test
    @DisplayName("Test update category")
    @Order(4)
    @Rollback(value = false)
    public void updateCategory() {
        Category category = Category.builder()
                .name("Laptop")
                .description("Laptop category")
                .build();
        categoryRepository.save(category);

        //Update
        category.setName("Mobile");
        category.setDescription("Mobile category");
        categoryRepository.save(category);

        //Verify
        Category updatedCategory = categoryRepository.findById(category.getId()).orElse(null);
        log.info("Updated category: {}", updatedCategory);
        Assertions.assertEquals("Mobile", updatedCategory.getName());
        Assertions.assertEquals("Mobile category", updatedCategory.getDescription());
    }

    @Test
    @DisplayName("Test delete category")
    @Order(5)
    @Rollback(value = false)
    public void deleteCategory() {
        Category category = Category.builder()
                .name("Laptop")
                .description("Laptop category")
                .build();
        categoryRepository.save(category);

        //Delete
        categoryRepository.deleteById(category.getId());

        //Verify
        Category deletedCategory = categoryRepository.findById(category.getId()).orElse(null);
        log.info("Deleted category: {}", deletedCategory);
        Assertions.assertNull(deletedCategory);
    }

}
