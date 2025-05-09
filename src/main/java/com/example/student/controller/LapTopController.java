package com.example.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.student.DTO.LaptopDTO;
import com.example.student.DTO.LaptopListResponseDTO;
import com.example.student.DTO.LaptopdetailsDTO;
import com.example.student.Service.LapTopService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("laptop")
public class LapTopController {
	
	 @Autowired
	    private LapTopService laptopService;

	    // GET all laptops
	 @RestController
	 @RequestMapping("/laptop")
	 public class LaptopController {

	     @Autowired
	     private LapTopService laptopService;

	     @GetMapping("/")
	     @Operation(
	    		 summary="To Get the All Laptop Details",
	    		 description="To Get the All Laptop Details"
	    		 )
	     public ResponseEntity<LaptopListResponseDTO> getAllLaptops(
	             @RequestParam(defaultValue = "1") int pageNo,
	             @RequestParam(defaultValue = "10") int pageSize) {

	         Pageable pageable = PageRequest.of(pageNo - 1, pageSize);  // Adjust for 1-based page number
	         LaptopListResponseDTO response = laptopService.getAllLaptopDetails(pageable);
	         return ResponseEntity.ok(response);
	     }
	 }


	    // GET a laptop by ID
	 @Operation(
    		 summary="To Get the All Laptop detail by Id",
    		 description="To Get the All Detail by Id"
    		 )
	    @GetMapping("/{laptopId}")
	    public ResponseEntity<LaptopdetailsDTO> getLaptopDetails(@PathVariable Integer laptopId) {
	        LaptopdetailsDTO laptopDetails = laptopService.getLaptopDetails(laptopId);
	        return ResponseEntity.ok(laptopDetails);
	    }

	    // POST a new laptop
	 @Operation(
			 summary="To add the All Laptop detail",
    		 description="To Add the All Detail"
			 )
	    @PostMapping("Add")
	    public ResponseEntity<?> saveLaptop(@RequestBody LaptopDTO dto) {
	        try {
	            LaptopDTO savedLaptop = laptopService.saveLaptop(dto);
	            return ResponseEntity.ok(savedLaptop);
	        } catch (DataIntegrityViolationException e) {
	            return ResponseEntity.badRequest().body("Laptop with this model number already exists.");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("Failed to save laptop. Please check the data and try again.");
	        }
	    }
	 @Operation(
			 summary="To Assign Laptop to student",
			 description="To Assign Laptop to student"
			 )
	    @PatchMapping("/assign/laptopId/{laptopId}/studentId/{studentId}")
	    public ResponseEntity<String> assignLaptopToStudent(@PathVariable Integer laptopId, @PathVariable Integer studentId){

	        // Call the service method to assign the laptop
	        String response = laptopService.assignLaptopToStudent(laptopId, studentId);
	        
	        if (response.equals("Laptop assigned to student successfully")) {
	            return ResponseEntity.ok(response);  // Return success response
	        } else {
	            return ResponseEntity.badRequest().body(response);  // Return failure response
	        }
	    }

}
