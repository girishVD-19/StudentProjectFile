package com.example.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.student.entity.Gd_Subject;

public interface SubjectRepository extends JpaRepository<Gd_Subject,Integer> {

}