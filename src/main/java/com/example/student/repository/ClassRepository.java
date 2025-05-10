package com.example.student.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.student.entity.Gd_Class;

public interface ClassRepository extends JpaRepository<Gd_Class,Integer> {
	@Query(
		    value = "SELECT c.CLASS_ID, c.CLASS_NAME, c.STD, " +
		            "r.ROOM_ID AS roomId, r.CAPACITY AS roomCapacity, " +
		            "s.SUBJECT_ID AS subjectId, s.SUBJECT_NAME AS subjectName " +
		            "FROM GD_CLASS c " +
		            "LEFT JOIN GD_ROOMS r ON r.ROOM_ID = c.ROOM_ID " +
		            "LEFT JOIN GD_SUBJECT_MAPPTING sm ON sm.CLASS_ID = c.CLASS_ID " +
		            "LEFT JOIN GD_SUBJECT s ON sm.SUBJECT_ID = s.SUBJECT_ID",
		    countQuery = "SELECT COUNT(DISTINCT c.CLASS_ID) FROM GD_CLASS c",
		    nativeQuery = true
		)
		Page<Object[]> findClassDetailsWithRoomAndSubjects(Pageable pageable);
		
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