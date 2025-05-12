package com.example.student.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            + "       h.RETURN_DATE AS returnDate, "
            + "       l.IS_ALIVE "
            + "FROM GD_LAPTOP l "
            + "LEFT JOIN GD_STUDENT s ON s.LAPTOP_ID = l.LAPTOP_ID "
            + "LEFT JOIN ("
            + "    SELECT HISTORY_ID, LAPTOP_ID, ASSIGNED_DATE, RETURN_DATE, "
            + "           ROW_NUMBER() OVER (PARTITION BY LAPTOP_ID ORDER BY ASSIGNED_DATE DESC) AS rn "
            + "    FROM GD_LAPTOP_HISTORY"
            + ") h ON h.LAPTOP_ID = l.LAPTOP_ID AND h.rn = 1 "
            + "WHERE (:isAssigned IS NULL OR l.IS_ASSIGNED = :isAssigned) "
            + "AND (:isAlive IS NULL OR l.IS_ALIVE = :isAlive) "
            + "ORDER BY l.LAPTOP_ID", 
      countQuery = "SELECT COUNT(*) FROM GD_LAPTOP l "
                 + "WHERE (:isAssigned IS NULL OR l.IS_ASSIGNED = :isAssigned) "
                 + "AND (:isAlive IS NULL OR l.IS_ALIVE = :isAlive)", 
      nativeQuery = true)
Page<Object[]> findAllLaptopDetailsWithFilters(Pageable pageable,
                                              @Param("isAssigned") Boolean isAssigned,
                                              @Param("isAlive") Boolean isAlive);

	@Query(value = "SELECT CASE WHEN EXISTS (SELECT 1 FROM GD_LAPTOP WHERE MODEL_NO = :modelNo) THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END", nativeQuery = true)
	boolean existsByModelNo(@Param("modelNo") int modelNo);
	
	@Query(value = "SELECT l.LAPTOP_ID, l.MODEL_NO, l.IS_ASSIGNED, "
            + "       s.STUDENT_ID AS studentId, s.NAME AS studentName, "
            + "       h.HISTORY_ID AS historyId, h.ASSIGNED_DATE AS assignedDate, "
            + "       h.RETURN_DATE AS returnDate "
            + "FROM GD_LAPTOP l "
            + "LEFT JOIN GD_STUDENT s ON s.LAPTOP_ID = l.LAPTOP_ID "
            + "LEFT JOIN GD_LAPTOP_HISTORY h ON h.LAPTOP_ID = l.LAPTOP_ID "
            + "WHERE l.LAPTOP_ID = :laptopId "
            + "ORDER BY h.ASSIGNED_DATE DESC", nativeQuery = true)
    List<Object[]> findLaptopDetailsWithAllHistoryById(@Param("laptopId") Integer laptopId);
	
	
	
	


    

}
