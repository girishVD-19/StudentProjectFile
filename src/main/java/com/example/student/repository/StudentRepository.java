package com.example.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.student.entity.Gd_Student;


public interface StudentRepository extends JpaRepository<Gd_Student,Integer>{

}