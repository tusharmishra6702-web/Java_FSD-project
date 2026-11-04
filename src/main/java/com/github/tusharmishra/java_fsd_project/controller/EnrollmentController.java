package com.github.tusharmishra.java_fsd_project.controller;

import com.github.tusharmishra.java_fsd_project.model.Course;
import com.github.tusharmishra.java_fsd_project.model.Enrollment;
import com.github.tusharmishra.java_fsd_project.model.EnrollmentId;
import com.github.tusharmishra.java_fsd_project.model.Student;
import com.github.tusharmishra.java_fsd_project.repository.CourseRepository;
import com.github.tusharmishra.java_fsd_project.repository.EnrollmentRepository;
import com.github.tusharmishra.java_fsd_project.repository.StudentRepository;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentController(EnrollmentRepository enrollmentRepository,
                                StudentRepository studentRepository,
                                CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    @GetMapping("/{studentId}/{courseId}")
    public Enrollment getEnrollmentById(@PathVariable Integer studentId, @PathVariable Integer courseId) {
        EnrollmentId id = new EnrollmentId(studentId, courseId);
        return enrollmentRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Enrollment createEnrollment(@RequestBody Enrollment enrollment) {
        Integer studentId = null;
        Integer courseId = null;

        if (enrollment.getId() != null) {
            studentId = enrollment.getId().getStudentId();
            courseId = enrollment.getId().getCourseId();
        }
        if (studentId == null && enrollment.getStudent() != null) {
            studentId = enrollment.getStudent().getStudentId() != null ? 
                        enrollment.getStudent().getStudentId() : enrollment.getStudent().getId();
        }
        if (courseId == null && enrollment.getCourse() != null) {
            courseId = enrollment.getCourse().getCourseId() != null ? 
                       enrollment.getCourse().getCourseId() : enrollment.getCourse().getId();
        }

        if (studentId != null && courseId != null) {
            Student student = studentRepository.findById(studentId).orElse(null);
            Course course = courseRepository.findById(courseId).orElse(null);

            enrollment.setId(new EnrollmentId(studentId, courseId));
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            return enrollmentRepository.save(enrollment);
        }
        return null;
    }

    @PutMapping("/{studentId}/{courseId}")
    public Enrollment updateEnrollment(@PathVariable Integer studentId, 
                                       @PathVariable Integer courseId, 
                                       @RequestBody Enrollment enrollment) {
        EnrollmentId id = new EnrollmentId(studentId, courseId);
        Enrollment existing = enrollmentRepository.findById(id).orElse(null);
        if (existing != null) {
            if (enrollment.getMarks() != null) {
                existing.setMarks(enrollment.getMarks());
            }
            if (enrollment.getAttendance() != null) {
                existing.setAttendance(enrollment.getAttendance());
            }
            return enrollmentRepository.save(existing);
        }
        return null;
    }

    @DeleteMapping("/{studentId}/{courseId}")
    public String deleteEnrollment(@PathVariable Integer studentId, @PathVariable Integer courseId) {
        EnrollmentId id = new EnrollmentId(studentId, courseId);
        enrollmentRepository.deleteById(id);
        return "Enrollment deleted for Student ID: " + studentId + " and Course ID: " + courseId;
    }

    @GetMapping("/student/{studentId}")
    public List<Enrollment> getEnrollmentsByStudentId(@PathVariable Integer studentId) {
        return enrollmentRepository.findByStudentStudentId(studentId);
    }

    @GetMapping("/course/{courseId}")
    public List<Enrollment> getEnrollmentsByCourseId(@PathVariable Integer courseId) {
        return enrollmentRepository.findByCourseCourseId(courseId);
    }

    @GetMapping("/high-performers")
    public List<Enrollment> getHighPerformers(@RequestParam(defaultValue = "80.00") BigDecimal minMarks) {
        return enrollmentRepository.findHighPerformers(minMarks);
    }

    @GetMapping("/low-attendance")
    public List<Enrollment> getLowAttendanceStudents(@RequestParam(defaultValue = "75.00") BigDecimal cutoff) {
        return enrollmentRepository.findLowAttendanceStudents(cutoff);
    }

    @GetMapping("/course-stats")
    public List<Object[]> getAverageMarksAndAttendancePerCourse() {
        return enrollmentRepository.averageMarksAndAttendancePerCourse();
    }
}
