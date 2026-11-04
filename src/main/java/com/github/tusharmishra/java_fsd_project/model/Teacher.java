package com.github.tusharmishra.java_fsd_project.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private Integer teacherId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "designation", length = 50)
    private String designation;

    @Column(name = "experience")
    private Integer experience;

    @Column(name = "salary", precision = 10, scale = 2)
    private BigDecimal salary;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dept_id")
    private Department department;

    public Teacher() {}

    public Teacher(String name, String designation, Integer experience, BigDecimal salary, Department department) {
        this.name = name;
        this.designation = designation;
        this.experience = experience;
        this.salary = salary;
        this.department = department;
    }

    public Teacher(Integer teacherId, String name, String designation, Integer experience, BigDecimal salary, Department department) {
        this.teacherId = teacherId;
        this.name = name;
        this.designation = designation;
        this.experience = experience;
        this.salary = salary;
        this.department = department;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    // Convenience alias for getId()
    public Integer getId() {
        return teacherId;
    }

    public void setId(Integer id) {
        this.teacherId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
