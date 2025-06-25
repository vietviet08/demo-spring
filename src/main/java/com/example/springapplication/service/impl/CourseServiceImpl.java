package com.example.springapplication.service.impl;

import com.example.springapplication.dto.request.CourseRequest;
import com.example.springapplication.dto.response.CourseResponse;
import com.example.springapplication.dto.response.StudentResponse;
import com.example.springapplication.dto.response.TeacherResponse;
import com.example.springapplication.model.*;
import com.example.springapplication.repository.CourseRepository;
import com.example.springapplication.repository.StudentRepository;
import com.example.springapplication.repository.TeacherRepository;
import com.example.springapplication.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseServiceImpl implements CourseService {
    
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    
    @Override
    public CourseResponse createCourse(CourseRequest request) {
        Teacher teacher = teacherRepository.findById(request.teacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        
        Course course = Course.builder()
                .title(request.title())
                .description(request.description())
                .teacher(teacher)
                .build();
        
        Course savedCourse = courseRepository.save(course);
        return mapToResponse(savedCourse);
    }
    
    @Override
    @Transactional(readOnly = true)
    public CourseResponse getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return mapToResponse(course);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CourseResponse.Simple> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(this::mapToSimpleResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CourseResponse.Simple> getCoursesByTeacher(Long teacherId) {
        return courseRepository.findByTeacherId(teacherId)
                .stream()
                .map(this::mapToSimpleResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CourseResponse.Simple> getCoursesByStudent(Long studentId) {
        return courseRepository.findByStudentId(studentId)
                .stream()
                .map(this::mapToSimpleResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public CourseResponse updateCourse(Long id, CourseRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
        Teacher teacher = teacherRepository.findById(request.teacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        
        course.setTitle(request.title());
        course.setDescription(request.description());
        course.setTeacher(teacher);
        
        Course updatedCourse = courseRepository.save(course);
        return mapToResponse(updatedCourse);
    }
    
    @Override
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new RuntimeException("Course not found");
        }
        courseRepository.deleteById(id);
    }
    
    @Override
    public CourseResponse enrollStudent(Long courseId, Long studentId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        if (course.getStudents().contains(student)) {
            throw new RuntimeException("Student already enrolled in this course");
        }
        
        course.getStudents().add(student);
        Course updatedCourse = courseRepository.save(course);
        return mapToResponse(updatedCourse);
    }
    
    @Override
    public CourseResponse unenrollStudent(Long courseId, Long studentId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        if (!course.getStudents().contains(student)) {
            throw new RuntimeException("Student is not enrolled in this course");
        }
        
        course.getStudents().remove(student);
        Course updatedCourse = courseRepository.save(course);
        return mapToResponse(updatedCourse);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CourseResponse.Simple> searchCourses(String keyword) {
        return courseRepository.findByTitleOrDescriptionContaining(keyword)
                .stream()
                .map(this::mapToSimpleResponse)
                .collect(Collectors.toList());
    }
    
    private CourseResponse mapToResponse(Course course) {
        TeacherResponse.Simple teacher = new TeacherResponse.Simple(
                course.getTeacher().getId(),
                course.getTeacher().getName(),
                course.getTeacher().getEmail()
        );
        
        List<StudentResponse.Simple> students = course.getStudents() != null ?
                course.getStudents().stream()
                        .map(this::mapStudentToSimpleResponse)
                        .collect(Collectors.toList()) : List.of();
        
        return new CourseResponse(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getCreatedAt(),
                teacher,
                students
        );
    }
    
    private CourseResponse.Simple mapToSimpleResponse(Course course) {
        TeacherResponse.Simple teacher = new TeacherResponse.Simple(
                course.getTeacher().getId(),
                course.getTeacher().getName(),
                course.getTeacher().getEmail()
        );
        
        return new CourseResponse.Simple(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getCreatedAt(),
                teacher
        );
    }
    
    private StudentResponse.Simple mapStudentToSimpleResponse(Student student) {
        return new StudentResponse.Simple(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getDob()
        );
    }
}
