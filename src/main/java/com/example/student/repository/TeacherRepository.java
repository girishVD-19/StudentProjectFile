package com.example.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.student.entity.Gd_Teacher;

public interface TeacherRepository extends JpaRepository<Gd_Teacher,Integer> {

}
