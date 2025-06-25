package com.example.springapplication.service;

import com.example.springapplication.dto.request.CourseRequest;
import com.example.springapplication.dto.response.CourseResponse;

import java.util.List;

public interface CourseService {
    
    CourseResponse createCourse(CourseRequest request);
    
    CourseResponse getCourseById(Long id);
    
    List<CourseResponse.Simple> getAllCourses();
    
    List<CourseResponse.Simple> getCoursesByTeacher(Long teacherId);
    
    List<CourseResponse.Simple> getCoursesByStudent(Long studentId);
    
    CourseResponse updateCourse(Long id, CourseRequest request);
    
    void deleteCourse(Long id);
    
    CourseResponse enrollStudent(Long courseId, Long studentId);
    
    CourseResponse unenrollStudent(Long courseId, Long studentId);
    
    List<CourseResponse.Simple> searchCourses(String keyword);
}
