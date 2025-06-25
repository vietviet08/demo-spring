package com.example.springapplication.service.impl;

import com.example.springapplication.dto.request.StudentRequest;
import com.example.springapplication.dto.response.CourseResponse;
import com.example.springapplication.dto.response.StudentResponse;
import com.example.springapplication.model.*;
import com.example.springapplication.repository.StudentRepository;
import com.example.springapplication.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {
    
    private final StudentRepository studentRepository;
    
    @Override
    public StudentResponse createStudent(StudentRequest request) {
        if (studentRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists");
        }
        
        Student student = Student.builder()
                .name(request.name())
                .email(request.email())
                .dob(request.dob())
                .build();
        
        Student savedStudent = studentRepository.save(student);
        return mapToResponse(savedStudent);
    }
    
    @Override
    @Transactional(readOnly = true)
    public StudentResponse getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return mapToResponse(student);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<StudentResponse.Simple> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::mapToSimpleResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public StudentResponse updateStudent(Long id, StudentRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        if (!student.getEmail().equals(request.email()) && 
            studentRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists");
        }
        
        student.setName(request.name());
        student.setEmail(request.email());
        student.setDob(request.dob());
        
        Student updatedStudent = studentRepository.save(student);
        return mapToResponse(updatedStudent);
    }
    
    @Override
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Student not found");
        }
        studentRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return studentRepository.existsByEmail(email);
    }
    
    private StudentResponse mapToResponse(Student student) {
        List<CourseResponse> courses = student.getCourses() != null ?
                student.getCourses().stream()
                        .map(this::mapCourseToResponse)
                        .collect(Collectors.toList()) : List.of();
        
        return new StudentResponse(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getDob(),
                courses
        );
    }
    
    private StudentResponse.Simple mapToSimpleResponse(Student student) {
        return new StudentResponse.Simple(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getDob()
        );
    }
    
    private CourseResponse mapCourseToResponse(Course course) {
        return new CourseResponse(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getCreatedAt(),
                null, // teacher - để null để tránh circular reference
                null  // students - để null để tránh circular reference
        );
    }
}
