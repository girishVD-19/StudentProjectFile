package com.example.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.student.entity.Gd_Class;

public interface ClassRepository extends JpaRepository<Gd_Class,Integer> {

}
