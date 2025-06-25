package com.example.springapplication.repository;

import com.example.springapplication.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    
    Optional<Teacher> findByEmail(String email);
    
    boolean existsByEmail(String email);
}
