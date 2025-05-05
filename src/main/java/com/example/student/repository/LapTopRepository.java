package com.example.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.student.entity.Gd_Laptop;

public interface LapTopRepository extends JpaRepository<Gd_Laptop,Integer> {
	
	@Query(value = "SELECT l.LAPTOP_ID, l.MODEL_NO, l.IS_ASSIGNED, "
	        + "       s.STUDENT_ID AS studentId, s.NAME AS studentName, "
	        + "       h.HISTORY_ID AS historyId, h.ASSIGNED_DATE AS assignedDate, "
	        + "       h.RETURN_DATE AS returnDate "
	        + "FROM GD_LAPTOP l "
	        + "LEFT JOIN GD_STUDENT s ON s.LAPTOP_ID = l.LAPTOP_ID "
	        + "LEFT JOIN ( "
	        + "    SELECT HISTORY_ID, LAPTOP_ID, ASSIGNED_DATE, RETURN_DATE, "
	        + "           ROW_NUMBER() OVER (PARTITION BY LAPTOP_ID ORDER BY HISTORY_ID DESC) AS rn "
	        + "    FROM GD_LAPTOP_HISTORY "
	        + ") h ON h.LAPTOP_ID = l.LAPTOP_ID AND h.rn = 1 "
	        + "WHERE l.LAPTOP_ID = :laptopId", nativeQuery = true)
	List<Object[]> findLaptopDetailsWithStudentAndRecentHistory(@Param("laptopId") Integer laptopId);


	@Query(value = "SELECT l.LAPTOP_ID, l.MODEL_NO, l.IS_ASSIGNED, "
	        + "       s.STUDENT_ID AS studentId, s.NAME AS studentName, "
	        + "       h.HISTORY_ID AS historyId, h.ASSIGNED_DATE AS assignedDate, "
	        + "       h.RETURN_DATE AS returnDate "
	        + "FROM GD_LAPTOP l "
	        + "LEFT JOIN GD_STUDENT s ON s.LAPTOP_ID = l.LAPTOP_ID "
	        + "LEFT JOIN ("
	        + "    SELECT HISTORY_ID,LAPTOP_ID, ASSIGNED_DATE, RETURN_DATE,"
	        + "           ROW_NUMBER() OVER (PARTITION BY LAPTOP_ID ORDER BY ASSIGNED_DATE DESC) AS rn "
	        + "    FROM GD_LAPTOP_HISTORY"
	        + ") h ON h.LAPTOP_ID = l.LAPTOP_ID AND h.rn = 1 "
	        + "ORDER BY l.LAPTOP_ID", nativeQuery = true) 
	List<Object[]> findAllLaptopDetailsWithStudentAndHistory();

	



}
