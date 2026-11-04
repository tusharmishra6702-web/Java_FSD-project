package com.github.tusharmishra.java_fsd_project.controller;

import com.github.tusharmishra.java_fsd_project.model.Department;
import com.github.tusharmishra.java_fsd_project.model.Student;
import com.github.tusharmishra.java_fsd_project.repository.DepartmentRepository;
import com.github.tusharmishra.java_fsd_project.repository.StudentRepository;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;

    public StudentController(StudentRepository studentRepository,
                             DepartmentRepository departmentRepository) {
        this.studentRepository = studentRepository;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Integer id) {
        return studentRepository.findById(id).orElse(null);
    }

    @GetMapping("/getById")
    public Student getStudentByIdParam(@RequestParam Integer id) {
        return studentRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        if (student.getDepartment() != null) {
            Integer deptId = student.getDepartment().getDeptId() != null ? 
                             student.getDepartment().getDeptId() : student.getDepartment().getId();
            if (deptId != null) {
                Department department = departmentRepository.findById(deptId).orElse(null);
                student.setDepartment(department);
            }
        }
        return studentRepository.save(student);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Integer id, @RequestBody Student student) {
        Student existing = studentRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(student.getName());
            existing.setCity(student.getCity());
            existing.setAdmissionYear(student.getAdmissionYear());
            existing.setCgpa(student.getCgpa());

            if (student.getDepartment() != null) {
                Integer deptId = student.getDepartment().getDeptId() != null ? 
                                 student.getDepartment().getDeptId() : student.getDepartment().getId();
                if (deptId != null) {
                    Department department = departmentRepository.findById(deptId).orElse(null);
                    existing.setDepartment(department);
                }
            }
            return studentRepository.save(existing);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Integer id) {
        studentRepository.deleteById(id);
        return "Student deleted with id: " + id;
    }

    @GetMapping("/by-department")
    public List<Student> getStudentsByDepartmentName(@RequestParam String name) {
        return studentRepository.findStudentsByDepartmentName(name);
    }

    @GetMapping("/by-city")
    public List<Student> getStudentsByCity(@RequestParam String city) {
        return studentRepository.findByCity(city);
    }

    @GetMapping("/high-cgpa")
    public List<Student> getStudentsWithCgpaAbove(@RequestParam(defaultValue = "8.0") BigDecimal minCgpa) {
        return studentRepository.findStudentsWithCgpaAbove(minCgpa);
    }

    @GetMapping("/city-counts")
    public List<Object[]> countStudentsPerCity() {
        return studentRepository.countStudentsPerCity();
    }

    @GetMapping("/department-stats")
    public List<Object[]> getDepartmentStats() {
        return studentRepository.studentStatsPerDepartment();
    }
}
