package com.github.tusharmishra.java_fsd_project.repository;

import com.github.tusharmishra.java_fsd_project.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    List<Course> findByDepartmentDeptId(Integer deptId);

    List<Course> findByCredits(Integer credits);

    @Query("SELECT c FROM Course c WHERE c.department.deptName = :deptName")
    List<Course> findCoursesByDepartmentName(@Param("deptName") String deptName);

    @Query("SELECT c FROM Course c WHERE c.credits >= :minCredits")
    List<Course> findCoursesByMinCredits(@Param("minCredits") Integer minCredits);
}
