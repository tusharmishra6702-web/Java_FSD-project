package com.github.tusharmishra.java_fsd_project.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Integer studentId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "city", length = 50)
    private String city;

    @Column(name = "admission_year")
    private Integer admissionYear;

    @Column(name = "cgpa", precision = 3, scale = 2)
    private BigDecimal cgpa;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dept_id")
    private Department department;

    public Student() {}

    public Student(String name, String city, Integer admissionYear, BigDecimal cgpa, Department department) {
        this.name = name;
        this.city = city;
        this.admissionYear = admissionYear;
        this.cgpa = cgpa;
        this.department = department;
    }

    public Student(Integer studentId, String name, String city, Integer admissionYear, BigDecimal cgpa, Department department) {
        this.studentId = studentId;
        this.name = name;
        this.city = city;
        this.admissionYear = admissionYear;
        this.cgpa = cgpa;
        this.department = department;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    // Convenience alias for getId()
    public Integer getId() {
        return studentId;
    }

    public void setId(Integer id) {
        this.studentId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getAdmissionYear() {
        return admissionYear;
    }

    public void setAdmissionYear(Integer admissionYear) {
        this.admissionYear = admissionYear;
    }

    public BigDecimal getCgpa() {
        return cgpa;
    }

    public void setCgpa(BigDecimal cgpa) {
        this.cgpa = cgpa;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
