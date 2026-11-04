package com.github.tusharmishra.java_fsd_project.repository;

import com.github.tusharmishra.java_fsd_project.model.Course;
import com.github.tusharmishra.java_fsd_project.model.Teacher;
import com.github.tusharmishra.java_fsd_project.model.Teaches;
import com.github.tusharmishra.java_fsd_project.model.TeachesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeachesRepository extends JpaRepository<Teaches, TeachesId> {

    List<Teaches> findByTeacherTeacherId(Integer teacherId);

    List<Teaches> findByCourseCourseId(Integer courseId);

    @Query("SELECT t.course FROM Teaches t WHERE t.teacher.teacherId = :teacherId")
    List<Course> findCoursesTaughtByTeacherId(@Param("teacherId") Integer teacherId);

    @Query("SELECT t.teacher FROM Teaches t WHERE t.course.courseId = :courseId")
    List<Teacher> findTeachersOfCourseId(@Param("courseId") Integer courseId);
}
