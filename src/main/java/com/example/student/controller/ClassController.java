package com.example.student.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.example.student.DTO.ClassResponseDTO;
import com.example.student.DTO.ClassSummary;
import com.example.student.DTO.ClassWithStudentDTO;
import com.example.student.DTO.PageSortDTO;
import com.example.student.Service.ClassService;


import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("class")
public class ClassController {
	
	@Autowired
	private ClassService classservice;
	
	@Operation(
			summary="To Get the data of a Whole Class" ,
			description="To Get the data of a Whole Class"
			)
	@GetMapping("/")
	public ResponseEntity<PageSortDTO<ClassDetailsDTO>> getAllClassDetails(
	        @RequestParam(defaultValue = "1") Integer pageNo,
	        @RequestParam(defaultValue = "10") Integer pageSize,
	        @RequestParam(required = false) String std) {

	    // Create Pageable object
	    Pageable pageable = PageRequest.of(pageNo - 1, pageSize); // PageRequest is zero-indexed

	    // Call service to fetch all class details with pagination
	    PageSortDTO<ClassDetailsDTO> response = classservice.getAllClassDetails(pageable, std);

	    return ResponseEntity.ok(response);
	}


     @Operation(
    		 summary="To Get Details of the Specific Class Id",
    		 description="To get the details of the Specific Class Id"
    		 )
	 @GetMapping("{classId}")
	    public ResponseEntity<ClassSummary> getClassDetails(@PathVariable Integer classId) {
	        try {
	            ClassSummary classDetails = classservice.getClassDetailsById(classId);  // Call service method to fetch data

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
     @Operation(
    		 summary="To Get Details of the Students with Class",
    		 description="To Get Details of the Students with Class"
    		 )
	 @GetMapping("/{classId}/details")
	    public ResponseEntity<ClassWithStudentDTO> getClassDetailsWithStudent(@PathVariable Integer classId) {
	        ClassWithStudentDTO classDetails = classservice.getClassWithStudents(classId);
	        return ResponseEntity.ok(classDetails);
	 }
	 
     @Operation(
    		 summary="To Add the Class Data.",
    		 description="To Send the class Details to  the DataBase."
               ) 
	 @PostMapping("/Add")
	 public ResponseEntity<String> createGdClass(@RequestBody ClassDetailsDTO dto) {
	     try {
	         String message = classservice.createGdClass(dto);
	         return ResponseEntity.ok(message);
	     } catch (Exception e) {
	         return ResponseEntity.badRequest().body("Cannot create class: duplicate or invalid data.");
	     }
	 }
     
@Operation(summary="To Update the the Class Room",
description="To update the class room details"
)
     @PatchMapping("update/{id}")
     public ResponseEntity<String> updateClassRoomFromJson(
             @PathVariable Integer id,
             @RequestBody Map<String, Integer> requestBody) {

         Integer roomId = requestBody.get("roomId");
         if (roomId == null) {
             return ResponseEntity.badRequest().body("Missing roomId in request body.");
         }

         try {
             classservice.updateClassRoom(id, roomId);
             return ResponseEntity.ok("Room updated successfully.");
         } catch (IllegalStateException e) {
             return ResponseEntity.badRequest().body(e.getMessage());  // e.g., "Room is not active"
         } catch (RuntimeException e) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
         }
     }

    @DeleteMapping("/{id}")
    @Operation(
   		 summary="To delete the Class Details.",
   		 description="To delete the Class Details With the given class id."
              ) 
    public ResponseEntity<Void> deleteClass(@PathVariable Integer id) {
        classservice.deleteGdClass(id);
        return ResponseEntity.noContent().build();
    }

}
