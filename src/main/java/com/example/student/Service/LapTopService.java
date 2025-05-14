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

	 public PageSortDTO<LaptopdetailsDTO> getAllLaptopDetails(Pageable pagable, Boolean isAssigned, Boolean isActive) {
		    // Fetch paginated results with filters applied
	
		    Page<Object[]> results = laptopRepository.findAllLaptopDetailsWithFilters(pagable, isAssigned, isActive);

		    List<LaptopdetailsDTO> laptopDetailsList = new ArrayList<>();
		    Map<Integer, LaptopdetailsDTO> laptopDetailsMap = new HashMap<>();
		  

		    // Iterate over the query results
		    for (Object[] row : results) {
		    	 System.out.println("Row data: " + Arrays.toString(row));
		    	 Integer laptopId = (Integer) row[0];
		    	 LaptopdetailsDTO dto = laptopDetailsMap.get(laptopId);	    	  

		        
		        if (dto == null) {
		            dto = new LaptopdetailsDTO();
		            dto.setLaptopId(laptopId);

		            // Handle model number (likely Integer)
		            dto.setModelNo((row[1] != null) ? ((Number) row[1]).intValue() : null);

		            // IS_ASSIGNED: Ensure proper casting (1 or 0)
		            if (row[2] != null) {
		                if (row[2] instanceof Integer) {
		                    dto.setIsAssigned((Integer) row[2]);
		                } else if (row[2] instanceof Boolean) {
		                    dto.setIsAssigned((Boolean) row[2] ? 1 : 0); // Convert Boolean to 1/0
		                }
		            }

		            // IS_ACTIVE: Ensure proper casting (1 or 0)
		            if (row[8] != null) {
		                if (row[8] instanceof Integer) {
		                    dto.setIsActive((Integer) row[8]);
		                } else if (row[8] instanceof Boolean) {
		                    dto.setIsActive((Boolean) row[8] ? 1 : 0); // Convert Boolean to 1/0
		                }
		            }

		            // Handle student info if assigned
		            if (dto.getIsAssigned() == 1) {
		                StudentDTO student = new StudentDTO();
		                student.setStudentId((row[3] != null) ? ((Number) row[3]).intValue() : null);
		                student.setStudentName((String) row[4]);

		                java.sql.Date assignedDate = (java.sql.Date) row[6];
		                if (assignedDate != null) {
		                    student.setAssignedDate(assignedDate.toLocalDate().toString());
		                }

		                dto.setStudents(student);  // One student assigned
		            } else {
		                dto.setStudents(new ArrayList<>());  // No student assigned
		            }

		            laptopDetailsMap.put(laptopId, dto);
		            laptopDetailsList.add(dto);
		        }
		    }
     
		    
		    
		    PageSortDTO.PaginationDetails paginationDetails = new PageSortDTO.PaginationDetails(
		            results.getNumber() + 1,  // Page number, incremented by 1 (human-friendly)
		            results.getTotalPages(),
		            (int) results.getTotalElements()
		    );

		    // Create and populate the response DTO with content and pagination details
		    PageSortDTO<LaptopdetailsDTO> response = new PageSortDTO<>(laptopDetailsList, paginationDetails);
		    
		    return response;
		}

	 public LaptopbyIdDTO getLaptopDetailsById(Integer laptopId) {
	        // Fetch all records for the laptop from the repository
	        List<Object[]> rows = laptopRepository.findLaptopDetailsWithAllHistoryById(laptopId);

	        if (rows.isEmpty()) {
	            return null; // Return null or throw exception if no laptop found
	        }

	        // List to hold the history records
	        List<LaptopbyIdDTO.HistoryDTO> historyList = new ArrayList<>();
	        LaptopbyIdDTO.StudentDTO student = null;

	        // Iterate over the rows and map data to the DTO
	        for (Object[] row : rows) {
	        	System.out.println(row);
	            // Set laptop details (Only once for the first row)
	            if (student == null) {
	                Integer studentId = row[3] != null ? (Integer) row[3] : null;  // Ensure null check
	                if (studentId != null) {
	                    student = new LaptopbyIdDTO.StudentDTO(
	                            studentId,                              // studentId
	                            row[4] != null ? (String) row[4] : null,  // studentName
	                            row[6] != null ? ((Date) row[6]).toString() : null // assignedDate
	                    );
	                } else {
	                    student = new LaptopbyIdDTO.StudentDTO(0, "", null); // No student assigned, return empty student
	                }
	            }

	            // Add history records
	            if (row[5] != null) { // If historyId exists
	                Integer historyId = (Integer) row[5];

	                // Check for historyStudentId and handle null
	                Integer historyStudentId = (row[3] != null) ? (Integer) row[3] : null;

	                // Convert assignedDate and returnDate to String
	                String historyAssignedDate = (row[6] != null) ? ((Date) row[6]).toString() : null;
	                String historyReturnDate = (row[7] != null) ? ((Date) row[7]).toString() : null;

	                // If historyStudentId is null, we assign a default value to avoid errors
	                if (historyStudentId != null) {
	                    LaptopbyIdDTO.HistoryDTO history = new LaptopbyIdDTO.HistoryDTO(
	                            historyId, historyStudentId, historyAssignedDate, historyReturnDate
	                    );
	                    historyList.add(history);
	                }
	            }
	        }

	        // Return the final DTO with student and history
	        return new LaptopbyIdDTO(
	                (Integer) rows.get(0)[0], // laptopId
	                (Integer) rows.get(0)[1], // modelNo
	                (Integer) rows.get(0)[2], // isAssigned
	                student,                  // student info inside an object
	                historyList               // all history records
	        );
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
