package com.github.tusharmishra.java_fsd_project.controller;

import com.github.tusharmishra.java_fsd_project.model.Department;
import com.github.tusharmishra.java_fsd_project.repository.DepartmentRepository;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentRepository departmentRepository;

    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable Integer id) {
        return departmentRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Department createDepartment(@RequestBody Department department) {
        return departmentRepository.save(department);
    }

    @PutMapping("/{id}")
    public Department updateDepartment(@PathVariable Integer id, @RequestBody Department department) {
        Department existing = departmentRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setDeptName(department.getDeptName());
            existing.setBuilding(department.getBuilding());
            existing.setBudget(department.getBudget());
            return departmentRepository.save(existing);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public String deleteDepartment(@PathVariable Integer id) {
        departmentRepository.deleteById(id);
        return "Department deleted with id: " + id;
    }

    @GetMapping("/high-budget")
    public List<Department> getHighBudgetDepartments(@RequestParam(defaultValue = "350000.00") BigDecimal minBudget) {
        return departmentRepository.findHighBudgetDepartments(minBudget);
    }

    @GetMapping("/teacher-counts")
    public List<Object[]> countTeachersPerDepartment() {
        return departmentRepository.countTeachersPerDepartment();
    }
}
