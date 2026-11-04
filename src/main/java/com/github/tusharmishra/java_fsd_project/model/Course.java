package com.github.tusharmishra.java_fsd_project.model;

import jakarta.persistence.*;

@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Integer courseId;

    @Column(name = "course_title", nullable = false, length = 150)
    private String courseTitle;

    @Column(name = "credits")
    private Integer credits;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dept_id")
    private Department department;

    public Course() {}

    public Course(String courseTitle, Integer credits, Department department) {
        this.courseTitle = courseTitle;
        this.credits = credits;
        this.department = department;
    }

    public Course(Integer courseId, String courseTitle, Integer credits, Department department) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.credits = credits;
        this.department = department;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    // Convenience alias for getId()
    public Integer getId() {
        return courseId;
    }

    public void setId(Integer id) {
        this.courseId = id;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
