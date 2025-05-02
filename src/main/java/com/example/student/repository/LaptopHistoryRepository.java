package com.example.student.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.student.DTO.LaptopHistoryDTO;
import com.example.student.entity.Gd_Laptop_History;

public interface LaptopHistoryRepository extends JpaRepository<Gd_Laptop_History,Integer> {
	@Query("SELECT h FROM Gd_Laptop_History h WHERE h.gd_student.STUDENT_ID = :studentId AND h.Return_Date IS NULL")
	Optional<Gd_Laptop_History> findActiveHistoryByStudentId(@Param("studentId") Integer studentId);
	
	@Query(value = "SELECT h.HISTORY_ID AS historyId, " +
            "h.LAPTOP_ID AS laptopId, " +
            "h.ASSIGNED_DATE AS assignedDate, " +
            "h.RETURN_DATE AS returnDate " +
            "FROM GD_LAPTOP_HISTORY h " +
            "WHERE h.STUDENT_ID = :studentId", nativeQuery = true)
List<LaptopHistoryDTO> findLaptopHistoryByStudentId(int studentId);

}
