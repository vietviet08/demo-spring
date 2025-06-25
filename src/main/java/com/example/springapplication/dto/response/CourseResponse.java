package com.example.springapplication.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record CourseResponse(
    Long id,
    String title,
    String description,
    LocalDateTime createdAt,
    TeacherResponse.Simple teacher,
    List<StudentResponse.Simple> students
) {
    // Record cho danh sách course (không bao gồm students để tránh N+1)
    public record Simple(
        Long id,
        String title,
        String description,
        LocalDateTime createdAt,
        TeacherResponse.Simple teacher
    ) {}
}
