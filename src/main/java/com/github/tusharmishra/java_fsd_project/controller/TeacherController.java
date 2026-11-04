package com.github.tusharmishra.java_fsd_project.controller;

import com.github.tusharmishra.java_fsd_project.model.Department;
import com.github.tusharmishra.java_fsd_project.model.Teacher;
import com.github.tusharmishra.java_fsd_project.repository.DepartmentRepository;
import com.github.tusharmishra.java_fsd_project.repository.TeacherRepository;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherRepository teacherRepository;
    private final DepartmentRepository departmentRepository;

    public TeacherController(TeacherRepository teacherRepository,
                             DepartmentRepository departmentRepository) {
        this.teacherRepository = teacherRepository;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @GetMapping("/{id}")
    public Teacher getTeacherById(@PathVariable Integer id) {
        return teacherRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Teacher createTeacher(@RequestBody Teacher teacher) {
        if (teacher.getDepartment() != null) {
            Integer deptId = teacher.getDepartment().getDeptId() != null ? 
                             teacher.getDepartment().getDeptId() : teacher.getDepartment().getId();
            if (deptId != null) {
                Department department = departmentRepository.findById(deptId).orElse(null);
                teacher.setDepartment(department);
            }
        }
        return teacherRepository.save(teacher);
    }

    @PutMapping("/{id}")
    public Teacher updateTeacher(@PathVariable Integer id, @RequestBody Teacher teacher) {
        Teacher existing = teacherRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(teacher.getName());
            existing.setDesignation(teacher.getDesignation());
            existing.setExperience(teacher.getExperience());
            existing.setSalary(teacher.getSalary());

            if (teacher.getDepartment() != null) {
                Integer deptId = teacher.getDepartment().getDeptId() != null ? 
                                 teacher.getDepartment().getDeptId() : teacher.getDepartment().getId();
                if (deptId != null) {
                    Department department = departmentRepository.findById(deptId).orElse(null);
                    existing.setDepartment(department);
                }
            }
            return teacherRepository.save(existing);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public String deleteTeacher(@PathVariable Integer id) {
        teacherRepository.deleteById(id);
        return "Teacher deleted with id: " + id;
    }

    @GetMapping("/by-department")
    public List<Teacher> getTeachersByDepartmentName(@RequestParam String name) {
        return teacherRepository.findTeachersByDepartmentName(name);
    }

    @GetMapping("/by-designation")
    public List<Teacher> getTeachersByDesignation(@RequestParam String designation) {
        return teacherRepository.findByDesignation(designation);
    }

    @GetMapping("/high-salary")
    public List<Teacher> getTeachersWithSalaryGreaterThan(@RequestParam(defaultValue = "50000.00") BigDecimal minSalary) {
        return teacherRepository.findTeachersWithSalaryGreaterThan(minSalary);
    }

    @GetMapping("/avg-salary-by-designation")
    public List<Object[]> getAverageSalaryByDesignation() {
        return teacherRepository.findAverageSalaryByDesignation();
    }
}
