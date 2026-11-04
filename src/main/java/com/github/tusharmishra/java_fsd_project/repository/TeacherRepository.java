package com.github.tusharmishra.java_fsd_project.repository;

import com.github.tusharmishra.java_fsd_project.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    List<Teacher> findByDepartmentDeptId(Integer deptId);

    List<Teacher> findByDesignation(String designation);

    @Query("SELECT t FROM Teacher t WHERE t.department.deptName = :deptName")
    List<Teacher> findTeachersByDepartmentName(@Param("deptName") String deptName);

    @Query("SELECT t FROM Teacher t WHERE t.salary >= :minSalary")
    List<Teacher> findTeachersWithSalaryGreaterThan(@Param("minSalary") BigDecimal minSalary);

    @Query("SELECT t.designation, AVG(t.salary) FROM Teacher t GROUP BY t.designation")
    List<Object[]> findAverageSalaryByDesignation();
}
