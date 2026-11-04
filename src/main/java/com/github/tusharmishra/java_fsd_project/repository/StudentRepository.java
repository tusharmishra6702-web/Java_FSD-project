package com.github.tusharmishra.java_fsd_project.repository;

import com.github.tusharmishra.java_fsd_project.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findByDepartmentDeptId(Integer deptId);

    List<Student> findByCity(String city);

    @Query("SELECT s FROM Student s JOIN s.department d WHERE d.deptName = :deptName")
    List<Student> findStudentsByDepartmentName(@Param("deptName") String deptName);

    @Query("SELECT s FROM Student s WHERE s.cgpa >= :minCgpa")
    List<Student> findStudentsWithCgpaAbove(@Param("minCgpa") BigDecimal minCgpa);

    @Query("SELECT s.city, COUNT(s) FROM Student s GROUP BY s.city")
    List<Object[]> countStudentsPerCity();

    @Query("SELECT d.deptName, COUNT(s), AVG(s.cgpa) FROM Student s JOIN s.department d GROUP BY d.deptName")
    List<Object[]> studentStatsPerDepartment();
}
