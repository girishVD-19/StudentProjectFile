package com.example.student.repository;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.student.entity.Gd_Class;

public interface ClassRepository extends JpaRepository<Gd_Class,Integer> {
	@Query(value = "SELECT c.CLASS_ID, c.CLASS_NAME, c.STD, r.ROOM_ID, r.CAPACITY " +
            "FROM GD_CLASS c " +
            "JOIN GD_ROOMS r ON c.ROOM_ID = r.ROOM_ID " +
            "WHERE (:std IS NULL OR c.STD = :std)",
    countQuery = "SELECT COUNT(*) FROM GD_CLASS c " +
                 "WHERE (:std IS NULL OR c.STD = :std)",
    nativeQuery = true)
Page<Object[]> findClassDetailsWithRoomAndSubjects(@Param("std") String std, Pageable pageable);

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