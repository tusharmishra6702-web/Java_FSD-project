package com.github.tusharmishra.java_fsd_project.controller;

import com.github.tusharmishra.java_fsd_project.model.Course;
import com.github.tusharmishra.java_fsd_project.model.Department;
import com.github.tusharmishra.java_fsd_project.repository.CourseRepository;
import com.github.tusharmishra.java_fsd_project.repository.DepartmentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;

    public CourseController(CourseRepository courseRepository,
                            DepartmentRepository departmentRepository) {
        this.courseRepository = courseRepository;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable Integer id) {
        return courseRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        if (course.getDepartment() != null) {
            Integer deptId = course.getDepartment().getDeptId() != null ? 
                             course.getDepartment().getDeptId() : course.getDepartment().getId();
            if (deptId != null) {
                Department department = departmentRepository.findById(deptId).orElse(null);
                course.setDepartment(department);
            }
        }
        return courseRepository.save(course);
    }

    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable Integer id, @RequestBody Course course) {
        Course existing = courseRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setCourseTitle(course.getCourseTitle());
            existing.setCredits(course.getCredits());

            if (course.getDepartment() != null) {
                Integer deptId = course.getDepartment().getDeptId() != null ? 
                                 course.getDepartment().getDeptId() : course.getDepartment().getId();
                if (deptId != null) {
                    Department department = departmentRepository.findById(deptId).orElse(null);
                    existing.setDepartment(department);
                }
            }
            return courseRepository.save(existing);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable Integer id) {
        courseRepository.deleteById(id);
        return "Course deleted with id: " + id;
    }

    @GetMapping("/by-department")
    public List<Course> getCoursesByDepartmentName(@RequestParam String name) {
        return courseRepository.findCoursesByDepartmentName(name);
    }

    @GetMapping("/by-credits")
    public List<Course> getCoursesByCredits(@RequestParam Integer credits) {
        return courseRepository.findByCredits(credits);
    }

    @GetMapping("/min-credits")
    public List<Course> getCoursesByMinCredits(@RequestParam(defaultValue = "3") Integer minCredits) {
        return courseRepository.findCoursesByMinCredits(minCredits);
    }
}
