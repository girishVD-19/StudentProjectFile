package com.example.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.student.entity.Gd_Class;

public interface ClassRepository extends JpaRepository<Gd_Class,Integer> {
	@Query(value = "SELECT c.CLASS_ID, c.CLASS_NAME, c.STD, " +
            "r.ROOM_ID AS roomId, r.CAPACITY AS roomCapacity, " +
            "s.SUBJECT_ID AS subjectId, s.SUBJECT_NAME AS subjectName " +
            "FROM GD_CLASS c " +
            "LEFT JOIN GD_ROOMS r ON r.ROOM_ID = c.ROOM_ID " +
            "LEFT JOIN GD_SUBJECT_MAPPTING sm ON sm.CLASS_ID = c.CLASS_ID " +
            "LEFT JOIN GD_SUBJECT s ON sm.SUBJECT_ID = s.SUBJECT_ID " +
            "WHERE c.CLASS_ID = :classId", nativeQuery = true)
List<Object[]> findClassDetailsWithRoomAndSubjects(@Param("classId") Integer classId);

@Query(value = "SELECT c.CLASS_ID, c.CLASS_NAME, c.STD, " +
        "r.ROOM_ID AS roomId, r.CAPACITY AS roomCapacity, " +
        "s.SUBJECT_ID AS subjectId, s.SUBJECT_NAME AS subjectName " +
        "FROM GD_CLASS c " +
        "LEFT JOIN GD_ROOMS r ON r.ROOM_ID = c.ROOM_ID " +
        "LEFT JOIN GD_SUBJECT_MAPPTING sm ON sm.CLASS_ID = c.CLASS_ID " +
        "LEFT JOIN GD_SUBJECT s ON sm.SUBJECT_ID = s.SUBJECT_ID",
nativeQuery = true)
List<Object[]> findAllClassDetailsWithRoomAndSubjects();

}
