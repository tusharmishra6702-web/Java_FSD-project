# University REST API Project

A 3-tier Spring Boot REST API for a University Database with 6 entities, built with Spring Data JPA and MySQL.

## Technology Stack

- Language: Java 17+
- Framework: Spring Boot
- Persistence: Spring Data JPA, Hibernate
- Database: MySQL
- Build Tool: Maven

## Database Schema and Entities

The project manages 6 relational entities mapped to the `SHIKHAR_BATCH` MySQL database:

1. Department (`department`)
   - Primary Key: `dept_id` (Integer, Auto-Increment)
   - Attributes: `dept_name` (String), `building` (String), `budget` (Decimal)

2. Teacher (`teacher`)
   - Primary Key: `teacher_id` (Integer, Auto-Increment)
   - Attributes: `name` (String), `designation` (String), `experience` (Integer), `salary` (Decimal)
   - Foreign Key: `dept_id` -> Department

3. Student (`student`)
   - Primary Key: `student_id` (Integer, Auto-Increment)
   - Attributes: `name` (String), `city` (String), `admission_year` (Integer), `cgpa` (Decimal)
   - Foreign Key: `dept_id` -> Department

4. Course (`course`)
   - Primary Key: `course_id` (Integer, Auto-Increment)
   - Attributes: `course_title` (String), `credits` (Integer)
   - Foreign Key: `dept_id` -> Department

5. Teaches (`teaches`)
   - Composite Primary Key: `teacher_id`, `course_id`
   - Junction table linking Teachers and Courses

6. Enrollment (`enrollment`)
   - Composite Primary Key: `student_id`, `course_id`
   - Attributes: `marks` (Decimal), `attendance` (Decimal)
   - Junction table linking Students and Courses with academic metrics

## Architecture

The project follows a standard 3-tier architecture:

- Model Layer (`com.github.tusharmishra.java_fsd_project.model`):
  JPA entities with `@Entity`, `@Table`, and `@EmbeddedId` for composite keys (`TeachesId`, `EnrollmentId`).
- Repository Layer (`com.github.tusharmishra.java_fsd_project.repository`):
  Spring Data JPA repositories providing CRUD operations and custom JPQL queries.
- Controller Layer (`com.github.tusharmishra.java_fsd_project.controller`):
  REST controllers implementing constructor-based dependency injection.

## Configuration

Database configuration in `src/main/resources/application.properties`:

```properties
spring.application.name=java-fsd-project

spring.datasource.url=jdbc:mysql://127.0.0.1:3306/SHIKHAR_BATCH?useSSL=false&serverTimezone=UTC&createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

server.port=8080
```

## Running the Application

### Prerequisites
- JDK 17 or higher
- MySQL Server running on localhost:3306

### Execution
Run the application using Maven wrapper:

```bash
./mvnw spring-boot:run
```

Or run the `main` method in `JavaFsdProjectApplication.java` from your IDE.

The server starts by default on `http://localhost:8080`.

## API Endpoints

### Department (`/api/departments`)
- GET `/api/departments` - Retrieve all departments
- GET `/api/departments/{id}` - Retrieve department by ID
- POST `/api/departments` - Create a department
- PUT `/api/departments/{id}` - Update a department
- DELETE `/api/departments/{id}` - Delete a department
- GET `/api/departments/high-budget?minBudget=...` - Filter departments by minimum budget
- GET `/api/departments/teacher-counts` - Count teachers per department

### Teacher (`/api/teachers`)
- GET `/api/teachers` - Retrieve all teachers
- GET `/api/teachers/{id}` - Retrieve teacher by ID
- POST `/api/teachers` - Create a teacher
- PUT `/api/teachers/{id}` - Update a teacher
- DELETE `/api/teachers/{id}` - Delete a teacher
- GET `/api/teachers/by-department?name=...` - Find teachers by department name
- GET `/api/teachers/by-designation?designation=...` - Find teachers by designation
- GET `/api/teachers/high-salary?minSalary=...` - Find teachers above salary threshold
- GET `/api/teachers/avg-salary-by-designation` - Average salary grouped by designation

### Student (`/api/students`)
- GET `/api/students` - Retrieve all students
- GET `/api/students/{id}` - Retrieve student by ID
- POST `/api/students` - Create a student
- PUT `/api/students/{id}` - Update a student
- DELETE `/api/students/{id}` - Delete a student
- GET `/api/students/by-department?name=...` - Find students by department name
- GET `/api/students/by-city?city=...` - Find students by city
- GET `/api/students/high-cgpa?minCgpa=...` - Find students with CGPA above threshold
- GET `/api/students/city-counts` - Student counts grouped by city
- GET `/api/students/department-stats` - Department statistics (count and average CGPA)

### Course (`/api/courses`)
- GET `/api/courses` - Retrieve all courses
- GET `/api/courses/{id}` - Retrieve course by ID
- POST `/api/courses` - Create a course
- PUT `/api/courses/{id}` - Update a course
- DELETE `/api/courses/{id}` - Delete a course
- GET `/api/courses/by-department?name=...` - Find courses by department name
- GET `/api/courses/by-credits?credits=...` - Find courses by credit count
- GET `/api/courses/min-credits?minCredits=...` - Filter courses by minimum credits

### Teaches (`/api/teaches`)
- GET `/api/teaches` - Retrieve all teacher-course assignments
- GET `/api/teaches/{teacherId}/{courseId}` - Retrieve assignment by composite key
- POST `/api/teaches` - Create teacher-course assignment
- DELETE `/api/teaches/{teacherId}/{courseId}` - Delete assignment
- GET `/api/teaches/teacher/{teacherId}/courses` - Get all courses taught by a teacher
- GET `/api/teaches/course/{courseId}/teachers` - Get all teachers assigned to a course

### Enrollment (`/api/enrollments`)
- GET `/api/enrollments` - Retrieve all student enrollments
- GET `/api/enrollments/{studentId}/{courseId}` - Retrieve enrollment by composite key
- POST `/api/enrollments` - Enroll student in course with marks and attendance
- PUT `/api/enrollments/{studentId}/{courseId}` - Update enrollment marks and attendance
- DELETE `/api/enrollments/{studentId}/{courseId}` - Delete enrollment
- GET `/api/enrollments/student/{studentId}` - Get all enrollments for a student
- GET `/api/enrollments/course/{courseId}` - Get all enrollments for a course
- GET `/api/enrollments/high-performers?minMarks=...` - Find students scoring above mark threshold
- GET `/api/enrollments/low-attendance?cutoff=...` - Find students below attendance cutoff
- GET `/api/enrollments/course-stats` - Course statistics (average marks and attendance)

