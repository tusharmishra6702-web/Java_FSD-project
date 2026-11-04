package com.github.tusharmishra.java_fsd_project.model;

import jakarta.persistence.*;

@Entity
@Table(name = "teaches")
public class Teaches {

    @EmbeddedId
    private TeachesId id = new TeachesId();

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("teacherId")
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private Course course;

    public Teaches() {}

    public Teaches(Teacher teacher, Course course) {
        this.teacher = teacher;
        this.course = course;
        if (teacher != null && course != null) {
            this.id = new TeachesId(teacher.getTeacherId(), course.getCourseId());
        }
    }

    public Teaches(TeachesId id, Teacher teacher, Course course) {
        this.id = id;
        this.teacher = teacher;
        this.course = course;
    }

    public TeachesId getId() {
        return id;
    }

    public void setId(TeachesId id) {
        this.id = id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
        if (this.id == null) {
            this.id = new TeachesId();
        }
        if (teacher != null) {
            this.id.setTeacherId(teacher.getTeacherId());
        }
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
        if (this.id == null) {
            this.id = new TeachesId();
        }
        if (course != null) {
            this.id.setCourseId(course.getCourseId());
        }
    }
}

