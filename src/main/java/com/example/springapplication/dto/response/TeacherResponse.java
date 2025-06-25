package com.example.springapplication.dto.response;

import java.util.List;

public record TeacherResponse(
    Long id,
    String name,
    String email,
    List<CourseResponse> courses
) {
    // Record cho danh sách teacher (không bao gồm courses để tránh N+1)
    public record Simple(
        Long id,
        String name,
        String email
    ) {}
}
