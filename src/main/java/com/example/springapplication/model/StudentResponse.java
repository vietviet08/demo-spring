package com.example.springapplication.model;

import java.time.LocalDate;
import java.util.List;

public record StudentResponse(
    Long id,
    String name,
    String email,
    LocalDate dob,
    List<CourseResponse> courses
) {
    
    // Response cho danh sách student (không bao gồm courses để tránh N+1)
    public record Simple(
        Long id,
        String name,
        String email,
        LocalDate dob
    ) {}
}
