package com.example.springapplication.service.impl;

import com.example.springapplication.dto.request.TeacherRequest;
import com.example.springapplication.dto.response.CourseResponse;
import com.example.springapplication.dto.response.TeacherResponse;
import com.example.springapplication.model.*;
import com.example.springapplication.repository.TeacherRepository;
import com.example.springapplication.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TeacherServiceImpl implements TeacherService {
    
    private final TeacherRepository teacherRepository;
    
    @Override
    public TeacherResponse createTeacher(TeacherRequest request) {
        if (teacherRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists");
        }
        
        Teacher teacher = Teacher.builder()
                .name(request.name())
                .email(request.email())
                .build();
        
        Teacher savedTeacher = teacherRepository.save(teacher);
        return mapToResponse(savedTeacher);
    }
    
    @Override
    @Transactional(readOnly = true)
    public TeacherResponse getTeacherById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        return mapToResponse(teacher);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TeacherResponse.Simple> getAllTeachers() {
        return teacherRepository.findAll()
                .stream()
                .map(this::mapToSimpleResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public TeacherResponse updateTeacher(Long id, TeacherRequest request) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        
        if (!teacher.getEmail().equals(request.email()) && 
            teacherRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists");
        }
        
        teacher.setName(request.name());
        teacher.setEmail(request.email());
        
        Teacher updatedTeacher = teacherRepository.save(teacher);
        return mapToResponse(updatedTeacher);
    }
    
    @Override
    public void deleteTeacher(Long id) {
        if (!teacherRepository.existsById(id)) {
            throw new RuntimeException("Teacher not found");
        }
        teacherRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return teacherRepository.existsByEmail(email);
    }
    
    private TeacherResponse mapToResponse(Teacher teacher) {
        List<CourseResponse> courses = teacher.getCourses() != null ?
                teacher.getCourses().stream()
                        .map(this::mapCourseToResponse)
                        .collect(Collectors.toList()) : List.of();
        
        return new TeacherResponse(
                teacher.getId(),
                teacher.getName(),
                teacher.getEmail(),
                courses
        );
    }
    
    private TeacherResponse.Simple mapToSimpleResponse(Teacher teacher) {
        return new TeacherResponse.Simple(
                teacher.getId(),
                teacher.getName(),
                teacher.getEmail()
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
