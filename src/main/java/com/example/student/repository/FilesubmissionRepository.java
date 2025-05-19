package com.example.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.student.entity.Gd_FileSubmission;

public interface FilesubmissionRepository extends JpaRepository<Gd_FileSubmission,Integer> {
	
	List<Gd_FileSubmission> findByUploaderUserId(Integer userId);


}
