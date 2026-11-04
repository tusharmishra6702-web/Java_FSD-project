package com.github.tusharmishra.java_fsd_project.repository;

import com.github.tusharmishra.java_fsd_project.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    Optional<Department> findByDeptName(String deptName);

    @Query("SELECT d FROM Department d WHERE d.budget >= :minBudget")
    List<Department> findHighBudgetDepartments(@Param("minBudget") BigDecimal minBudget);

    @Query("SELECT d.deptName, COUNT(t) FROM Teacher t RIGHT JOIN t.department d GROUP BY d.deptName")
    List<Object[]> countTeachersPerDepartment();
}
