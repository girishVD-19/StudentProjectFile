package com.example.student.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.student.DTO.LaptopDTO;
import com.example.student.DTO.LaptopdetailsDTO;
import com.example.student.entity.Gd_Laptop;
import com.example.student.entity.Gd_Student;
import com.example.student.repository.LapTopRepository;



@Service
public class LapTopService {

	 @Autowired
	    private LapTopRepository laptopRepository;

	
	 public List<LaptopdetailsDTO> getAllLaptopDetails() {
		    List<Object[]> results = laptopRepository.findAllLaptopDetailsWithStudentAndHistory();
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

		        // Add history record (only latest history if needed)
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
		            // Only include studentId in history if returnDate is not null
		          
		        } else {
		            history.setReturnDate(null);
		        }

		        dto.getHistories().add(history);
		    }

		    return laptopDetailsList;
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
	        Gd_Laptop laptop = new Gd_Laptop();
	        laptop.setMODEL_NO(dto.getModelNo());
	        laptop.setIS_ASSIGNED(0); 
	        Gd_Laptop saved = laptopRepository.save(laptop);
	        return mapToDTO(saved);
	    }

	    private LaptopDTO mapToDTO(Gd_Laptop laptop) {
	        List<Integer> studentIds = new ArrayList<>();

	        // If students are assigned to the laptop, include their IDs
	        if (laptop.getGd_student() != null) {
	            for (Gd_Student student : laptop.getGd_student()) {
	                studentIds.add(student.getSTUDENT_ID());  // Get the student ID
	            }
	        }

	        return new LaptopDTO(
	                laptop.getLAPTOP_ID(),
	                laptop.getMODEL_NO(),
	                laptop.getIS_ASSIGNED(),
	                studentIds  // Pass list of studentIds
	        );
	    }

}
