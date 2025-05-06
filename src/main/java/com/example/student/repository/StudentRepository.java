package com.example.student.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.student.entity.Gd_Student;


public interface StudentRepository extends JpaRepository<Gd_Student,Integer>{
	
	 @Query(value = "SELECT * FROM gd_student s " +
             "WHERE (:name IS NULL OR LOWER(s.NAME) LIKE LOWER(CONCAT('%', :name, '%'))) " +
             "AND (:city IS NULL OR LOWER(s.CITY) LIKE LOWER(CONCAT('%', :city, '%')))", 
     nativeQuery = true)
Page<Gd_Student> findByFilters(@Param("name") String name, 
                              @Param("city") String city, 
                              Pageable pageable);
}