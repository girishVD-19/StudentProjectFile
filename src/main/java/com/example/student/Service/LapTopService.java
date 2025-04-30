package com.example.student.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.student.DTO.LaptopDTO;

import com.example.student.entity.Gd_Laptop;
import com.example.student.entity.Gd_Student;
import com.example.student.repository.LapTopRepository;



@Service
public class LapTopService {

	 @Autowired
	    private LapTopRepository laptopRepository;

	
	    public List<LaptopDTO> getAllLaptops() {
	        return laptopRepository.findAll().stream()
	            .map(this::mapToDTO)
	            .collect(Collectors.toList());
	    }

	
	    public LaptopDTO getLaptopById(Integer id) {
	        Gd_Laptop laptop = laptopRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Laptop not found with id: " + id));
	        return mapToDTO(laptop);
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
