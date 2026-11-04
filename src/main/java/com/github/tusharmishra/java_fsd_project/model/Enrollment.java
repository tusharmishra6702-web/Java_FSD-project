package com.github.tusharmishra.java_fsd_project.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "enrollment")
public class Enrollment {

    @EmbeddedId
    private EnrollmentId id = new EnrollmentId();

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "marks", precision = 5, scale = 2)
    private BigDecimal marks;

    @Column(name = "attendance", precision = 5, scale = 2)
    private BigDecimal attendance;

    public Enrollment() {}

    public Enrollment(Student student, Course course, BigDecimal marks, BigDecimal attendance) {
        this.student = student;
        this.course = course;
        this.marks = marks;
        this.attendance = attendance;
        if (student != null && course != null) {
            this.id = new EnrollmentId(student.getStudentId(), course.getCourseId());
        }
    }

    public Enrollment(EnrollmentId id, Student student, Course course, BigDecimal marks, BigDecimal attendance) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.marks = marks;
        this.attendance = attendance;
    }

    public EnrollmentId getId() {
        return id;
    }

    public void setId(EnrollmentId id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
        if (this.id == null) {
            this.id = new EnrollmentId();
        }
        if (student != null) {
            this.id.setStudentId(student.getStudentId());
        }
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
        if (this.id == null) {
            this.id = new EnrollmentId();
        }
        if (course != null) {
            this.id.setCourseId(course.getCourseId());
        }
    }

    public BigDecimal getMarks() {
        return marks;
    }

    public void setMarks(BigDecimal marks) {
        this.marks = marks;
    }

    public BigDecimal getAttendance() {
        return attendance;
    }

    public void setAttendance(BigDecimal attendance) {
        this.attendance = attendance;
    }
}

