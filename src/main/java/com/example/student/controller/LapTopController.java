package com.example.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.student.DTO.LaptopDTO;
import com.example.student.DTO.LaptopdetailsDTO;
import com.example.student.Service.LapTopService;

@RestController
@RequestMapping("laptop")
public class LapTopController {
	
	 @Autowired
	    private LapTopService laptopService;

	    // GET all laptops
	 @GetMapping("all")
	    public ResponseEntity<List<LaptopdetailsDTO>> getAllLaptopDetails() {
	        List<LaptopdetailsDTO> laptopDetailsList = laptopService.getAllLaptopDetails();
	        if (laptopDetailsList != null && !laptopDetailsList.isEmpty()) {
	            return ResponseEntity.ok(laptopDetailsList);
	        } else {
	            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	        }
	    }

	    // GET a laptop by ID
	    @GetMapping("/{laptopId}")
	    public ResponseEntity<LaptopdetailsDTO> getLaptopDetails(@PathVariable Integer laptopId) {
	        LaptopdetailsDTO laptopDetails = laptopService.getLaptopDetails(laptopId);
	        return ResponseEntity.ok(laptopDetails);
	    }

	    // POST a new laptop
	    @PostMapping("Add")
	    public ResponseEntity<LaptopDTO> saveLaptop(@RequestBody LaptopDTO dto) {
	        LaptopDTO savedLaptop = laptopService.saveLaptop(dto);
	        return ResponseEntity.ok(savedLaptop);
	    }

}
