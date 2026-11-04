package com.github.tusharmishra.java_fsd_project.repository;

import com.github.tusharmishra.java_fsd_project.model.Enrollment;
import com.github.tusharmishra.java_fsd_project.model.EnrollmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentId> {

    List<Enrollment> findByStudentStudentId(Integer studentId);

    List<Enrollment> findByCourseCourseId(Integer courseId);

    @Query("SELECT e FROM Enrollment e WHERE e.marks >= :minMarks")
    List<Enrollment> findHighPerformers(@Param("minMarks") BigDecimal minMarks);

    @Query("SELECT e FROM Enrollment e WHERE e.attendance < :cutoff")
    List<Enrollment> findLowAttendanceStudents(@Param("cutoff") BigDecimal cutoff);

    @Query("SELECT e.course.courseTitle, AVG(e.marks), AVG(e.attendance) FROM Enrollment e GROUP BY e.course.courseTitle")
    List<Object[]> averageMarksAndAttendancePerCourse();
}
