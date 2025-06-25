package com.example.springapplication.service;

import com.example.springapplication.dto.request.StudentRequest;
import com.example.springapplication.dto.response.StudentResponse;

import java.util.List;

public interface StudentService {
    
    StudentResponse createStudent(StudentRequest request);
    
    StudentResponse getStudentById(Long id);
    
    List<StudentResponse.Simple> getAllStudents();
    
    StudentResponse updateStudent(Long id, StudentRequest request);
    
    void deleteStudent(Long id);
    
    boolean existsByEmail(String email);
}
