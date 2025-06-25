package com.example.springapplication.service;

import com.example.springapplication.dto.request.TeacherRequest;
import com.example.springapplication.dto.response.TeacherResponse;

import java.util.List;

public interface TeacherService {
    
    TeacherResponse createTeacher(TeacherRequest request);
    
    TeacherResponse getTeacherById(Long id);
    
    List<TeacherResponse.Simple> getAllTeachers();
    
    TeacherResponse updateTeacher(Long id, TeacherRequest request);
    
    void deleteTeacher(Long id);
    
    boolean existsByEmail(String email);
}
