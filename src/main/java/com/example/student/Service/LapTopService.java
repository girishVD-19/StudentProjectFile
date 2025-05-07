package com.example.student.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.student.DTO.LaptopDTO;
import com.example.student.DTO.LaptopListResponseDTO;
import com.example.student.DTO.LaptopdetailsDTO;
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

	
	 public LaptopListResponseDTO getAllLaptopDetails(Pageable pageable) {
		    // Fetch paginated data from the repository
		    Page<Object[]> results = laptopRepository.findAllLaptopDetailsWithStudentAndHistory(pageable);

		    // List to hold the final laptop details DTOs
		    List<LaptopdetailsDTO> laptopDetailsList = new ArrayList<>();
		    Map<Integer, LaptopdetailsDTO> laptopDetailsMap = new HashMap<>();
		    Set<Integer> studentIds = new HashSet<>();

		    // Iterate through all results
		    for (Object[] row : results) {
		        Integer laptopId = (Integer) row[0];

		        // Check if laptop details already exist in the map, else create new
		        LaptopdetailsDTO dto = laptopDetailsMap.get(laptopId);
		        if (dto == null) {
		            dto = new LaptopdetailsDTO();
		            dto.setLaptopId(laptopId);
		            dto.setModelNo((Integer) row[1]);
		            dto.setIsAssigned((Integer) row[2]);
		            dto.setStudents(new ArrayList<>());  // Initialize the students list
		            dto.setHistories(new ArrayList<>()); // Initialize the histories list
		            laptopDetailsMap.put(laptopId, dto);
		            laptopDetailsList.add(dto);  // Add to list only once
		        }

		        // Add student if not already added
		        Integer studentId = (Integer) row[3];
		        if (!studentIds.contains(studentId)) {
		            LaptopdetailsDTO.StudentDTO student = new LaptopdetailsDTO.StudentDTO();
		            student.setStudentId(studentId);
		            student.setStudentName((String) row[4]);
		            dto.getStudents().add(student);
		            studentIds.add(studentId);
		        }

		        // Add history record
		        LaptopdetailsDTO.HistoryDTO history = new LaptopdetailsDTO.HistoryDTO();
		        history.setHistoryId((Integer) row[5]);

		        // Check if ASSIGNED_DATE is null
		        java.sql.Date assignedDate = (java.sql.Date) row[6];
		        if (assignedDate != null) {
		            history.setAssignedDate(assignedDate.toLocalDate());
		        } else {
		            history.setAssignedDate(null);
		        }

		        // Check if RETURN_DATE is null
		        java.sql.Date returnDate = (java.sql.Date) row[7];
		        if (returnDate != null) {
		            history.setReturnDate(returnDate.toLocalDate());
		        } else {
		            history.setReturnDate(null);
		        }

		        dto.getHistories().add(history);
		    }

		    // Create the response with pagination metadata
		    LaptopListResponseDTO response = new LaptopListResponseDTO();
		    response.setContent(laptopDetailsList);  // List of laptop details DTOs
		    response.setTotalElements(results.getTotalElements());
		    response.setTotalPages(results.getTotalPages());
		    response.setPageNumber(results.getNumber() + 1);  // Adjust page number to start from 1
		    response.setPageSize(results.getSize());

		    return response;
		}

        
	 public LaptopdetailsDTO getLaptopDetails(Integer laptopId) {
		    List<Object[]> results = laptopRepository.findLaptopDetailsWithStudentAndRecentHistory(laptopId);
		    LaptopdetailsDTO dto = new LaptopdetailsDTO();
		    Set<Integer> studentIds = new HashSet<>();

		    for (Object[] row : results) {
		        // Set basic laptop details
		        dto.setLaptopId((Integer) row[0]);
		        dto.setModelNo((Integer) row[1]);
		        dto.setIsAssigned((Integer) row[2]);

		        // Add student details if not already added
		        Integer studentId = (Integer) row[3];
		        if (!studentIds.contains(studentId)) {
		            LaptopdetailsDTO.StudentDTO student = new LaptopdetailsDTO.StudentDTO();
		            student.setStudentId(studentId);
		            student.setStudentName((String) row[4]);
		            dto.getStudents().add(student);
		            studentIds.add(studentId);
		        }

		        // Add recent history record
		        LaptopdetailsDTO.HistoryDTO history = new LaptopdetailsDTO.HistoryDTO();
		        history.setHistoryId((Integer) row[5]);

		        java.sql.Date assignedDate = (java.sql.Date) row[6];
		        if (assignedDate != null) {
		            history.setAssignedDate(assignedDate.toLocalDate());
		        }

		        java.sql.Date returnDate = (java.sql.Date) row[7];
		        if (returnDate != null) {
		            history.setReturnDate(returnDate.toLocalDate());
		        }

		        dto.getHistories().add(history);
		    }

		    return dto;
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
