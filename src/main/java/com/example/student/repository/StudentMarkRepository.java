package com.example.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.student.entity.Gd_Student_Mark;

public interface StudentMarkRepository extends JpaRepository<Gd_Student_Mark,Integer> {
	
	

}
