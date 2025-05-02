package com.example.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.student.entity.Gd_Student_Mark;

public interface StudentMarkRepository extends JpaRepository<Gd_Student_Mark,Integer> {
	
	@Query("SELECT m FROM Gd_Student_Mark m WHERE m.gd_student.STUDENT_ID = :studentId")
    List<Gd_Student_Mark> findMarksByStudentId(@Param("studentId") int studentId);

}
