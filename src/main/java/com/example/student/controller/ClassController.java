package com.example.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.student.DTO.ClassDetailsDTO;
import com.example.student.Service.ClassService;

@RestController
@RequestMapping("class")
public class ClassController {
	
	@Autowired
	private ClassService classservice;
	
	@GetMapping("All")
	public ResponseEntity<List<ClassDetailsDTO>> getAllClassDetails() {
        List<ClassDetailsDTO> classDetailsList = classservice.getAllClassDetails();
        return new ResponseEntity<>(classDetailsList, HttpStatus.OK);
    }

	 @GetMapping("{classId}")
	    public ResponseEntity<ClassDetailsDTO> getClassDetails(@PathVariable Integer classId) {
	        try {
	            ClassDetailsDTO classDetails = classservice.getClassDetails(classId);  // Call service method to fetch data

	            if (classDetails == null) {
	                // If class not found, return a 404 Not Found response
	                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	            }

	            return new ResponseEntity<>(classDetails, HttpStatus.OK);  // Return class details with HTTP status 200 OK
	        } catch (Exception e) {
	            // If any exception occurs, return 500 Internal Server Error
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

    @PostMapping("Add")
    public ResponseEntity<String> createGdClass(@RequestBody ClassDetailsDTO dto) {
        String message = classservice.createGdClass(dto);
        return ResponseEntity.ok(message);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<ClassDetailsDTO> updateClass(
            @PathVariable Integer id,
            @RequestBody ClassDetailsDTO classDto,
            @RequestParam Integer roomId
    ) {
        return ResponseEntity.ok(classservice.updateGdClass(id, classDto, roomId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable Integer id) {
        classservice.deleteGdClass(id);
        return ResponseEntity.noContent().build();
    }

}
