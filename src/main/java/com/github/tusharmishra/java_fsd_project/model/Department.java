package com.github.tusharmishra.java_fsd_project.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_id")
    private Integer deptId;

    @Column(name = "dept_name", nullable = false, length = 100)
    private String deptName;

    @Column(name = "building", length = 50)
    private String building;

    @Column(name = "budget", precision = 12, scale = 2)
    private BigDecimal budget;

    public Department() {}

    public Department(String deptName, String building, BigDecimal budget) {
        this.deptName = deptName;
        this.building = building;
        this.budget = budget;
    }

    public Department(Integer deptId, String deptName, String building, BigDecimal budget) {
        this.deptId = deptId;
        this.deptName = deptName;
        this.building = building;
        this.budget = budget;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    // Convenience alias for getId()
    public Integer getId() {
        return deptId;
    }

    public void setId(Integer id) {
        this.deptId = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }
}
