# Hệ Thống Quản Lý Khóa Học

Đây là hệ thống quản lý khóa học được xây dựng bằng Spring Boot với các tính năng quản lý giảng viên, sinh viên và khóa học.

## Mô Hình Dữ Liệu

### Teacher (Giảng viên)
- `id`: Long (Primary Key)
- `name`: String (Tên giảng viên)
- `email`: String (Email - duy nhất)

### Student (Sinh viên)
- `id`: Long (Primary Key)
- `name`: String (Tên sinh viên)
- `email`: String (Email - duy nhất)
- `dob`: LocalDate (Ngày sinh)

### Course (Khóa học)
- `id`: Long (Primary Key)
- `title`: String (Tiêu đề khóa học)
- `description`: String (Mô tả)
- `createdAt`: LocalDateTime (Thời gian tạo)
- `teacher`: Teacher (Giảng viên phụ trách)
- `students`: List<Student> (Danh sách sinh viên đăng ký)

## Quan Hệ

- **Course ↔ Teacher**: @ManyToOne (Nhiều khóa học - Một giảng viên)
- **Teacher ↔ Course**: @OneToMany (Một giảng viên - Nhiều khóa học)
- **Student ↔ Course**: @ManyToMany (Nhiều sinh viên - Nhiều khóa học)

## API Endpoints

### Teacher APIs

#### 1. Tạo giảng viên mới
```
POST /api/teachers
Content-Type: application/json

{
  "name": "Nguyễn Văn A",
  "email": "teacher@example.com"
}
```

#### 2. Lấy danh sách tất cả giảng viên
```
GET /api/teachers
```

#### 3. Lấy thông tin giảng viên theo ID
```
GET /api/teachers/{id}
```

#### 4. Cập nhật thông tin giảng viên
```
PUT /api/teachers/{id}
Content-Type: application/json

{
  "name": "Nguyễn Văn A Updated",
  "email": "teacher_updated@example.com"
}
```

#### 5. Xóa giảng viên
```
DELETE /api/teachers/{id}
```

### Student APIs

#### 1. Tạo sinh viên mới
```
POST /api/students
Content-Type: application/json

{
  "name": "Trần Thị B",
  "email": "student@example.com",
  "dob": "2000-01-15"
}
```

#### 2. Lấy danh sách tất cả sinh viên
```
GET /api/students
```

#### 3. Lấy thông tin sinh viên theo ID
```
GET /api/students/{id}
```

#### 4. Cập nhật thông tin sinh viên
```
PUT /api/students/{id}
Content-Type: application/json

{
  "name": "Trần Thị B Updated",
  "email": "student_updated@example.com",
  "dob": "2000-01-15"
}
```

#### 5. Xóa sinh viên
```
DELETE /api/students/{id}
```

### Course APIs

#### 1. Tạo khóa học mới
```
POST /api/courses
Content-Type: application/json

{
  "title": "Spring Boot Fundamentals",
  "description": "Khóa học cơ bản về Spring Boot",
  "teacherId": 1
}
```

#### 2. Lấy danh sách tất cả khóa học
```
GET /api/courses
```

#### 3. Lấy thông tin khóa học theo ID
```
GET /api/courses/{id}
```

#### 4. Lấy danh sách khóa học của một giảng viên
```
GET /api/courses/teacher/{teacherId}
```

#### 5. Lấy danh sách khóa học của một sinh viên
```
GET /api/courses/student/{studentId}
```

#### 6. Tìm kiếm khóa học
```
GET /api/courses/search?keyword=spring
```

#### 7. Cập nhật thông tin khóa học
```
PUT /api/courses/{id}
Content-Type: application/json

{
  "title": "Advanced Spring Boot",
  "description": "Khóa học nâng cao về Spring Boot",
  "teacherId": 1
}
```

#### 8. Đăng ký sinh viên vào khóa học
```
POST /api/courses/{courseId}/enroll/{studentId}
```

#### 9. Hủy đăng ký sinh viên khỏi khóa học
```
DELETE /api/courses/{courseId}/enroll/{studentId}
```

#### 10. Xóa khóa học
```
DELETE /api/courses/{id}
```

## Cách Chạy Ứng Dụng

### 1. Chuẩn bị Database
Đảm bảo MySQL đang chạy trên port 3307 với:
- Database: `demo_spring`
- Username: `root`
- Password: `root`

### 2. Chạy ứng dụng
```bash
./mvnw spring-boot:run
```

Ứng dụng sẽ chạy trên port 8088: http://localhost:8088

### 3. Test APIs
Sử dụng Postman hoặc curl để test các APIs. Ví dụ:

```bash
# Tạo giảng viên
curl -X POST http://localhost:8088/api/teachers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Giảng viên A",
    "email": "teacher1@example.com"
  }'

# Tạo sinh viên
curl -X POST http://localhost:8088/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Sinh viên B",
    "email": "student1@example.com",
    "dob": "2000-01-15"
  }'

# Tạo khóa học
curl -X POST http://localhost:8088/api/courses \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Spring Boot Basics",
    "description": "Khóa học cơ bản về Spring Boot",
    "teacherId": 1
  }'

# Đăng ký sinh viên vào khóa học
curl -X POST http://localhost:8088/api/courses/1/enroll/1
```

## Tính Năng

✅ CRUD operations cho Teacher, Student, Course
✅ Quan hệ Many-to-One giữa Course và Teacher
✅ Quan hệ Many-to-Many giữa Student và Course
✅ Đăng ký/Hủy đăng ký sinh viên vào khóa học
✅ Tìm kiếm khóa học theo từ khóa
✅ Validation input data
✅ Exception handling toàn cục
✅ Response DTOs để tránh vấn đề N+1 query

## Cơ Sở Dữ Liệu

Ứng dụng sử dụng JPA/Hibernate với auto DDL để tự động tạo các bảng:
- `teachers`
- `students` 
- `courses`
- `student_courses` (bảng trung gian cho many-to-many)
