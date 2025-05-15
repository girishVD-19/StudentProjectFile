package com.example.student.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.student.DTO.LaptopAssignmentDTO;
import com.example.student.DTO.LaptopDTO;
import com.example.student.DTO.LaptopListResponseDTO;
import com.example.student.DTO.LaptopbyIdDTO;
import com.example.student.DTO.LaptopdetailsDTO;
import com.example.student.DTO.LaptopdetailsDTO.StudentDTO;
import com.example.student.DTO.PageSortDTO;
import com.example.student.entity.Gd_Laptop;
import com.example.student.entity.Gd_Laptop_History;
import com.example.student.entity.Gd_Student;
import com.example.student.repository.LapTopRepository;
import com.example.student.repository.LaptopHistoryRepository;
import com.example.student.repository.StudentRepository;





@Service
public class LapTopService {

	 @Autowired
	    private LapTopRepository laptopRepository;
	 
	 @Autowired
	 private StudentRepository studentrepository;
	 
	 @Autowired
	 private LaptopHistoryRepository laptophistoryrepository;
	 
	 @Autowired
	    private NamedParameterJdbcTemplate jdbcTemplate;

	 public PageSortDTO<LaptopdetailsDTO> getAllLaptopDetails(Pageable pageable, Boolean isAssigned, Boolean isActive) {
		    // Construct SQL query for pagination using OFFSET-FETCH for SQL Server
		    String query = "SELECT l.LAPTOP_ID, l.MODEL_NO, l.IS_ASSIGNED, "
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
		            + "ORDER BY l.LAPTOP_ID "
		            + "OFFSET :offset ROWS FETCH NEXT :limit ROWS ONLY";

		    // Parameters for the query
		    Map<String, Object> params = new HashMap<>();
		    params.put("isAssigned", isAssigned);
		    params.put("isAlive", isActive);
		    params.put("limit", pageable.getPageSize());  // Page size limit
		    params.put("offset", pageable.getPageNumber() * pageable.getPageSize());  // Calculate offset

		    // Query to get the paginated list of laptops
		    List<LaptopdetailsDTO> laptopDetailsList = jdbcTemplate.query(query, params, (rs, rowNum) -> {
		        LaptopdetailsDTO dto = new LaptopdetailsDTO();
		        dto.setLaptopId(rs.getInt("LAPTOP_ID"));
		        dto.setModelNo(rs.getInt("MODEL_NO"));
		        dto.setIsAssigned(rs.getInt("IS_ASSIGNED"));
		        dto.setIsActive(rs.getInt("IS_ALIVE"));

		        // Handle student info if assigned
		        if (dto.getIsAssigned() == 1) {
		            LaptopdetailsDTO.StudentDTO student = new LaptopdetailsDTO.StudentDTO();
		            student.setStudentId(rs.getInt("studentId"));
		            student.setStudentName(rs.getString("studentName"));
		            student.setAssignedDate(rs.getDate("assignedDate").toLocalDate().toString());
		            dto.setStudents(student); // Set the student (single student, not a list)
		        } else {
		            dto.setStudents(null); // Set null if no student is assigned
		        }

		        return dto;
		    });

		    // Query to get the total count (same as before)
		    String countQuery = "SELECT COUNT(*) FROM GD_LAPTOP l "
		            + "WHERE (:isAssigned IS NULL OR l.IS_ASSIGNED = :isAssigned) "
		            + "AND (:isAlive IS NULL OR l.IS_ALIVE = :isAlive)";

		    int totalElements = jdbcTemplate.queryForObject(countQuery, params, Integer.class);
		    int totalPages = (int) Math.ceil((double) totalElements / pageable.getPageSize());

		    // Pagination details
		    PageSortDTO.PaginationDetails paginationDetails = new PageSortDTO.PaginationDetails(
		            pageable.getPageNumber() + 1, // Page number (human-friendly)
		            totalPages,
		            totalElements
		    );

		    return new PageSortDTO<>(laptopDetailsList, paginationDetails);
		}

	 public LaptopbyIdDTO getLaptopDetailsById(Integer laptopId) {
		    // SQL query to get the laptop details along with student and history information
		    String query = "SELECT l.LAPTOP_ID, l.MODEL_NO, l.IS_ASSIGNED, "
		            + "       s.STUDENT_ID AS studentId, s.NAME AS studentName, "
		            + "       h.HISTORY_ID AS historyId, h.ASSIGNED_DATE AS assignedDate, "
		            + "       h.RETURN_DATE AS returnDate "
		            + "FROM GD_LAPTOP l "
		            + "LEFT JOIN GD_STUDENT s ON s.LAPTOP_ID = l.LAPTOP_ID "
		            + "LEFT JOIN GD_LAPTOP_HISTORY h ON h.LAPTOP_ID = l.LAPTOP_ID "
		            + "WHERE l.LAPTOP_ID = :laptopId "
		            + "ORDER BY h.ASSIGNED_DATE DESC";

		    // Parameters map for the query
		    Map<String, Object> params = new HashMap<>();
		    params.put("laptopId", laptopId);

		    // Execute the query using NamedParameterJdbcTemplate
		    List<LaptopbyIdDTO> laptopDetailsList = jdbcTemplate.query(query, params, (rs, rowNum) -> {
		        // First, map the laptop details
		        Integer laptopIdValue = rs.getInt("LAPTOP_ID");
		        Integer modelNo = rs.getInt("MODEL_NO");
		        Integer isAssigned = rs.getInt("IS_ASSIGNED");

		        // Initialize student and history list
		        LaptopbyIdDTO.StudentDTO student = null;
		        List<LaptopbyIdDTO.HistoryDTO> historyList = new ArrayList<>();

		        // Map student details (Only once for the first row)
		        if (student == null) {
		            Integer studentId = rs.getInt("studentId");
		            if (studentId != 0) {
		                student = new LaptopbyIdDTO.StudentDTO(
		                        studentId, 
		                        rs.getString("studentName"), 
		                        rs.getDate("assignedDate") != null ? rs.getDate("assignedDate").toString() : null
		                );
		            } else {
		                student = new LaptopbyIdDTO.StudentDTO(0, "", null); // No student assigned, return empty student
		            }
		        }

		        // Map history details
		        Integer historyId = rs.getInt("historyId");
		        if (historyId != 0) { // Only add history if exists
		            String assignedDate = rs.getDate("assignedDate") != null ? rs.getDate("assignedDate").toString() : null;
		            String returnDate = rs.getDate("returnDate") != null ? rs.getDate("returnDate").toString() : null;

		            LaptopbyIdDTO.HistoryDTO history = new LaptopbyIdDTO.HistoryDTO(
		                    historyId, student != null ? student.getStudentId() : null, assignedDate, returnDate
		            );
		            historyList.add(history);
		        }

		        // Return the LaptopbyIdDTO object with student and history details
		        return new LaptopbyIdDTO(
		                laptopIdValue, modelNo, isAssigned, student, historyList
		        );
		    });

		    // If no results were returned, return null
		    return laptopDetailsList.isEmpty() ? null : laptopDetailsList.get(0);
		}

	 
	 public String assignLaptopToStudent(int laptopId, int studentId) {
	        // Retrieve the laptop and student entities
	        Optional<Gd_Laptop> laptopOpt = laptopRepository.findById(laptopId);
	        Optional<Gd_Student> studentOpt = studentrepository.findById(studentId);

	        if (!laptopOpt.isPresent()) {
	            return "Laptop not found.";
	        }

	        if (!studentOpt.isPresent()) {
	            return "Student not found.";
	        }

	        Gd_Laptop laptop = laptopOpt.get();
	        Gd_Student student = studentOpt.get();

	        // Check if the laptop is already assigned
	        if (laptop.getIS_ASSIGNED() == 1) {
	            return "Laptop is already assigned to another student.";
	        }

	        // Check if the laptop is active
	        if (!laptop.getIS_ALIVE()) {
	            return "Laptop is not active and cannot be assigned.";
	        }

	        // Assign the laptop to the student
	        laptop.setIS_ASSIGNED(1);
	        laptop.setGd_student(Collections.singletonList(student)); // Assuming Gd_Student has a reference to Gd_Laptop
	        laptopRepository.save(laptop);

	        // Update the student's laptop reference
	        student.setGd_laptop(laptop);
	        studentrepository.save(student);

	        // Create and save the laptop history record
	        Gd_Laptop_History history = new Gd_Laptop_History();
	        history.setGd_laptop(laptop);
	        history.setGd_student(student);
	        history.setASSIGNED_DATE(LocalDate.now());
	        history.setReturn_Date(null); // Laptop is currently assigned, so return date is null
	        laptophistoryrepository.save(history);

	        return "Laptop assigned to student successfully, history updated.";
	    }

	 public LaptopDTO saveLaptop(LaptopDTO dto) {
		 try {
	            // Check if a laptop with the same modelNo already exists
	            if (laptopRepository.existsByModelNo(dto.getModelNo())) {
	                throw new DataIntegrityViolationException("Laptop with this model number already exists.");
	            }

	            // Convert DTO to entity
	            Gd_Laptop laptop = new Gd_Laptop();
	            laptop.setLAPTOP_ID(dto.getLaptopId());
	            laptop.setMODEL_NO(dto.getModelNo());
	            laptop.setIS_ALIVE(true);
	            laptop.setIS_ASSIGNED(dto.isAssigned() ? 1 : 0); // Convert boolean to int (1 or 0)

	            // If the laptop is assigned, associate the students
	            if (dto.isAssigned()) {
	                List<Gd_Student> students = studentrepository.findAllById(dto.getStudentIds());
	                laptop.setGd_student(students); // Assuming the relation is 'gd_student'
	            }

	            // Save the laptop entity
	            laptop = laptopRepository.save(laptop);

	            // Convert the saved entity back to DTO
	            LaptopDTO savedLaptopDTO = new LaptopDTO();
	            savedLaptopDTO.setLaptopId(laptop.getLAPTOP_ID());
	            savedLaptopDTO.setModelNo(laptop.getMODEL_NO());
	            savedLaptopDTO.setAssigned(laptop.getIS_ASSIGNED() == 1); // Convert int back to boolean

	            // Return the saved LaptopDTO
	            return savedLaptopDTO;

	        } catch (DataIntegrityViolationException e) {
	            // Handle case when modelNo already exists
	            throw new IllegalArgumentException("Laptop with this model number already exists.", e);
	        } catch (Exception e) {
	            // Handle other exceptions
	            throw new RuntimeException("Failed to save laptop. Please check the data and try again.", e);
	        }
	    }
	
	       

	 @Transactional
	    public String assignLaptopToStudent(Integer laptopId, Integer studentId) {
	        // 1. Check if the laptop exists
	        Optional<Gd_Laptop> laptopOptional = laptopRepository.findById(laptopId);
	        if (!laptopOptional.isPresent()) {
	            return "Laptop not found";
	        }

	        Gd_Laptop laptop = laptopOptional.get();
	        
	        if(laptop.getIS_ALIVE()==false) {
	        	return "Laptop is not Alive";
	        }

	        // 2. Check if the laptop is already assigned
	        if (laptop.getIS_ASSIGNED() == 1) {
	            return "Laptop is already assigned";
	        }

	        // 3. Get the student from the database
	        Optional<Gd_Student> studentOptional = studentrepository.findById(studentId);
	        if (!studentOptional.isPresent()) {
	            return "Student not found";
	        }

	        Gd_Student student = studentOptional.get();
	        
	        Gd_Laptop oldLaptop = student.getGd_laptop();
	        if (oldLaptop != null) {
	            oldLaptop.setIS_ASSIGNED(0);               // Mark old laptop as unassigned
	            laptopRepository.save(oldLaptop);          // Save change to DB
	        }

	        // 4. Assign the laptop to the student
	        laptop.setIS_ASSIGNED(1); // Set IS_ASSIGNED to 1

	        // Add the laptop to the student's list of laptops (if not already present)
	        if (student.getGd_laptop() == null) {
	            student.setGd_laptop_history(new ArrayList<>());
	        }
	        student.setGd_laptop(laptop); // Properly assign the laptop to the student
// Associate the laptop with the student\
	        
	        Gd_Laptop_History lastAssignedHistory = laptophistoryrepository
	                .findLatestAssignedLaptopHistory(studentId, oldLaptop.getLAPTOP_ID());
	        if (lastAssignedHistory != null) {
	            lastAssignedHistory.setReturn_Date(LocalDate.now());
	            laptophistoryrepository.save(lastAssignedHistory);
	        }      
	        // 5. Create a new laptop history record for the assignment
	        Gd_Laptop_History history = new Gd_Laptop_History();
	        history.setGd_laptop(laptop);
	        history.setGd_student(student);
	        history.setASSIGNED_DATE(LocalDate.now()); // Use the current date as the assigned date
	        history.setReturn_Date(null); // Set the return date as null for now (the laptop is assigned)
	        laptophistoryrepository.save(history); // Save the history record

	        // 6. Save the updated student and laptop
	        studentrepository.save(student);  // Save the student
	        laptopRepository.save(laptop);    // Save the laptop

	        return "Laptop assigned to student successfully";
	    }

}
