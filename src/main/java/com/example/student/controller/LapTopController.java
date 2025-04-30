package com.example.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.student.DTO.LaptopDTO;
import com.example.student.Service.LapTopService;

@RestController
@RequestMapping("laptop")
public class LapTopController {
	
	 @Autowired
	    private LapTopService laptopService;

	    // GET all laptops
	    @GetMapping("All")
	    public ResponseEntity<List<LaptopDTO>> getAllLaptops() {
	        List<LaptopDTO> laptops = laptopService.getAllLaptops();
	        return ResponseEntity.ok(laptops);
	    }

	    // GET a laptop by ID
	    @GetMapping("/{id}")
	    public ResponseEntity<LaptopDTO> getLaptopById(@PathVariable Integer id) {
	        LaptopDTO laptop = laptopService.getLaptopById(id);
	        return ResponseEntity.ok(laptop);
	    }

	    // POST a new laptop
	    @PostMapping("Add")
	    public ResponseEntity<LaptopDTO> saveLaptop(@RequestBody LaptopDTO dto) {
	        LaptopDTO savedLaptop = laptopService.saveLaptop(dto);
	        return ResponseEntity.ok(savedLaptop);
	    }

}
