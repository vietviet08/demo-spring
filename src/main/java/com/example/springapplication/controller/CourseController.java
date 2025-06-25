package com.example.springapplication.controller;

import com.example.springapplication.dto.request.CourseRequest;
import com.example.springapplication.dto.response.CourseResponse;
import com.example.springapplication.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {
    
    private final CourseService courseService;
    
    @PostMapping
    public ResponseEntity<CourseResponse> createCourse(@Valid @RequestBody CourseRequest request) {
        CourseResponse response = courseService.createCourse(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> getCourseById(@PathVariable Long id) {
        CourseResponse response = courseService.getCourseById(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    public ResponseEntity<List<CourseResponse.Simple>> getAllCourses() {
        List<CourseResponse.Simple> response = courseService.getAllCourses();
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<CourseResponse.Simple>> getCoursesByTeacher(@PathVariable Long teacherId) {
        List<CourseResponse.Simple> response = courseService.getCoursesByTeacher(teacherId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<CourseResponse.Simple>> getCoursesByStudent(@PathVariable Long studentId) {
        List<CourseResponse.Simple> response = courseService.getCoursesByStudent(studentId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<CourseResponse.Simple>> searchCourses(@RequestParam String keyword) {
        List<CourseResponse.Simple> response = courseService.searchCourses(keyword);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CourseResponse> updateCourse(
            @PathVariable Long id, 
            @Valid @RequestBody CourseRequest request) {
        CourseResponse response = courseService.updateCourse(id, request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{courseId}/enroll/{studentId}")
    public ResponseEntity<CourseResponse> enrollStudent(
            @PathVariable Long courseId, 
            @PathVariable Long studentId) {
        CourseResponse response = courseService.enrollStudent(courseId, studentId);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{courseId}/enroll/{studentId}")
    public ResponseEntity<CourseResponse> unenrollStudent(
            @PathVariable Long courseId, 
            @PathVariable Long studentId) {
        CourseResponse response = courseService.unenrollStudent(courseId, studentId);
        return ResponseEntity.ok(response);
    }
}
