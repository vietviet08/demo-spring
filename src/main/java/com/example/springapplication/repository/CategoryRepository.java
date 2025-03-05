package com.example.springapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springapplication.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
