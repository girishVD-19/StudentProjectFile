package com.example.student.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.student.DTO.LaptopHistoryDTO;
import com.example.student.entity.Gd_Laptop;
import com.example.student.entity.Gd_Laptop_History;
import com.example.student.entity.Gd_Student;

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

	@Query(value = "SELECT TOP 1 * FROM gd_laptop_history WHERE student_id = :studentId AND laptop_id = :laptopId AND return_date IS NULL ORDER BY assigned_date DESC", nativeQuery = true)
	Gd_Laptop_History findLatestAssignedLaptopHistory(@Param("studentId") Integer studentId, @Param("laptopId") Integer laptopId);

	

}
