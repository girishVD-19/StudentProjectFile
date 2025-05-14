package com.example.student.repository;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.student.DTO.ClassDetailsDTO;
import com.example.student.DTO.ClassResponseDTO;
import com.example.student.entity.Gd_Class;

public interface ClassRepository extends JpaRepository<Gd_Class,Integer> {
	
	@Query("SELECT new com.example.student.DTO.ClassDetailsDTO(c.CLASS_ID, c.CLASS_NAME, c.STD, " +
	           "new com.example.student.DTO.RoomDTO(r.ROOM_ID, r.Capacity)) " +
	           "FROM Gd_Class c JOIN c.gd_rooms r WHERE c.STD = :std")
	    Page<ClassDetailsDTO> findClassDetailsWithRoom(@Param("std") String std, Pageable pageable);
	
	@Query("SELECT new com.example.student.DTO.ClassDetailsDTO(c.CLASS_ID, c.CLASS_NAME, c.STD, " +
		       "new com.example.student.DTO.RoomDTO(r.ROOM_ID, r.Capacity)) " +
		       "FROM Gd_Class c LEFT JOIN c.gd_rooms r")
		Page<ClassDetailsDTO> findAllClassDetails(Pageable pageable);

		@Query(value = "SELECT " +
		        "c.class_id, c.class_name, c.std, " +
		        "r.room_id, r.capacity, " +
		        "(SELECT STRING_AGG(CAST(s.subject_id AS VARCHAR), ',') " +
		        " FROM gd_subject_mappting sm " +
		        " JOIN gd_subject s ON sm.subject_id = s.subject_id " +
		        " WHERE sm.class_id = c.class_id) AS subject_ids, " +
		        "(SELECT STRING_AGG(s.subject_name, ',') " +
		        " FROM gd_subject_mappting sm " +
		        " JOIN gd_subject s ON sm.subject_id = s.subject_id " +
		        " WHERE sm.class_id = c.class_id) AS subject_names, " +
		        "(SELECT STRING_AGG(CAST(st.student_id AS VARCHAR), ',') " +
		        " FROM gd_student st WHERE st.class_id = c.class_id) AS student_ids, " +
		        "(SELECT STRING_AGG(st.name, ',') " +
		        " FROM gd_student st WHERE st.class_id = c.class_id) AS student_names " +
		        "FROM gd_class c " +
		        "LEFT JOIN gd_rooms r ON c.room_id = r.room_id " +
		        "WHERE c.class_id = :classId",
		        nativeQuery = true)
		List<Object[]> findClassWithRelationsNative(@Param("classId") Integer classId);

		}