package com.example.student.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.student.entity.Gd_Review;

public interface ReviewRepository extends JpaRepository<Gd_Review,Integer> {
	
}
