
package com.example.springapplication.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springapplication.model.Category;
import com.example.springapplication.model.CategoryResponse;
import com.example.springapplication.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping("/category")
  public ResponseEntity<List<Category>> findAll() {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(categoryService.findAll());
  }

  @GetMapping("/category/{id}")
  public ResponseEntity<Category> findById(@PathVariable Long id) {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(categoryService.findById(id));
  }

  @PostMapping("/category")
  public ResponseEntity<CategoryResponse> save(@RequestBody Category category) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(categoryService.save(category));
  }

  @PutMapping("/category/{id}")
  public ResponseEntity<Category> update(@PathVariable Long id,
                                         @RequestBody Category category) {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(categoryService.update(id, category));
  }

  @DeleteMapping("/category/{id}")
  public ResponseEntity<Void> deleteById(@PathVariable Long id) {
    categoryService.deleteById(id);
    return ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .build();
  }
}
