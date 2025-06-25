package com.example.springapplication.model;

import java.util.List;

public record TeacherResponse(
    Long id,
    String name,
    String email,
    List<CourseResponse> courses
) {
    
    // Response cho danh sách teacher (không bao gồm courses để tránh N+1)
    public record Simple(
        Long id,
        String name,
        String email
    ) {}
}
