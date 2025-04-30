package com.example.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.student.entity.Gd_Subject_Mapping;

public interface SubjectMappingRepository extends JpaRepository<Gd_Subject_Mapping,Integer> {

}
