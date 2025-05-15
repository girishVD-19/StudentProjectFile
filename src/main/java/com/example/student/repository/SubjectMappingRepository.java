package com.example.student.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.student.entity.Gd_Subject_Mapping;

public interface SubjectMappingRepository extends JpaRepository<Gd_Subject_Mapping,Integer> {
	
	@Query("SELECT sm FROM Gd_Subject_Mapping sm " +
		       "WHERE (:classId IS NULL OR sm.gd_class.CLASS_ID = :classId) " +
		       "AND (:subjectId IS NULL OR sm.gd_subject.SUBJECT_ID = :subjectId)")
		Page<Gd_Subject_Mapping> findAllWithFilter(
		        @Param("classId") Integer classId,
		        @Param("subjectId") Integer subjectId,
		        Pageable pageable);



	

}