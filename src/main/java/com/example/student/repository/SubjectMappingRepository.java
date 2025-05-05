package com.example.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.student.entity.Gd_Subject_Mapping;

public interface SubjectMappingRepository extends JpaRepository<Gd_Subject_Mapping,Integer> {
	

}