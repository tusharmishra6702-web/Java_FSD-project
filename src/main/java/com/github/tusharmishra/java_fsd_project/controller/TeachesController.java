package com.github.tusharmishra.java_fsd_project.controller;

import com.github.tusharmishra.java_fsd_project.model.Course;
import com.github.tusharmishra.java_fsd_project.model.Teacher;
import com.github.tusharmishra.java_fsd_project.model.Teaches;
import com.github.tusharmishra.java_fsd_project.model.TeachesId;
import com.github.tusharmishra.java_fsd_project.repository.CourseRepository;
import com.github.tusharmishra.java_fsd_project.repository.TeacherRepository;
import com.github.tusharmishra.java_fsd_project.repository.TeachesRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teaches")
public class TeachesController {

    private final TeachesRepository teachesRepository;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;

    public TeachesController(TeachesRepository teachesRepository,
                             TeacherRepository teacherRepository,
                             CourseRepository courseRepository) {
        this.teachesRepository = teachesRepository;
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public List<Teaches> getAllTeaches() {
        return teachesRepository.findAll();
    }

    @GetMapping("/{teacherId}/{courseId}")
    public Teaches getTeachesById(@PathVariable Integer teacherId, @PathVariable Integer courseId) {
        TeachesId id = new TeachesId(teacherId, courseId);
        return teachesRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Teaches createTeaches(@RequestBody Teaches teaches) {
        Integer teacherId = null;
        Integer courseId = null;

        if (teaches.getId() != null) {
            teacherId = teaches.getId().getTeacherId();
            courseId = teaches.getId().getCourseId();
        }
        if (teacherId == null && teaches.getTeacher() != null) {
            teacherId = teaches.getTeacher().getTeacherId() != null ? 
                        teaches.getTeacher().getTeacherId() : teaches.getTeacher().getId();
        }
        if (courseId == null && teaches.getCourse() != null) {
            courseId = teaches.getCourse().getCourseId() != null ? 
                       teaches.getCourse().getCourseId() : teaches.getCourse().getId();
        }

        if (teacherId != null && courseId != null) {
            Teacher teacher = teacherRepository.findById(teacherId).orElse(null);
            Course course = courseRepository.findById(courseId).orElse(null);

            teaches.setId(new TeachesId(teacherId, courseId));
            teaches.setTeacher(teacher);
            teaches.setCourse(course);
            return teachesRepository.save(teaches);
        }
        return null;
    }

    @DeleteMapping("/{teacherId}/{courseId}")
    public String deleteTeaches(@PathVariable Integer teacherId, @PathVariable Integer courseId) {
        TeachesId id = new TeachesId(teacherId, courseId);
        teachesRepository.deleteById(id);
        return "Teaches assignment deleted for Teacher ID: " + teacherId + " and Course ID: " + courseId;
    }

    @GetMapping("/teacher/{teacherId}/courses")
    public List<Course> getCoursesByTeacherId(@PathVariable Integer teacherId) {
        return teachesRepository.findCoursesTaughtByTeacherId(teacherId);
    }

    @GetMapping("/course/{courseId}/teachers")
    public List<Teacher> getTeachersByCourseId(@PathVariable Integer courseId) {
        return teachesRepository.findTeachersOfCourseId(courseId);
    }
}
