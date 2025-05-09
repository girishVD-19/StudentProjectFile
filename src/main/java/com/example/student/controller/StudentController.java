package com.example.student.controller;


import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.student.DTO.StudentListResponseDTO;
import com.example.student.DTO.StudentResponseDTO;
import com.example.student.Service.StudentService;
import com.example.student.entity.Gd_Student;

import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/Student")
public class StudentController {
	    
	    @Autowired
	    private StudentService studentService;
	    
	    @Operation(
	    		summary="To Get All Details of the Students",
	    		description="To Get All Details of the Students"
	    		)
	    @GetMapping("/")
	    public ResponseEntity<StudentListResponseDTO> getAllStudents(
	            @RequestParam(required = false) String name,
	            @RequestParam(required = false) String city,
	            Pageable pageable) {

	        StudentListResponseDTO response = studentService.getAllStudents(name, city, pageable);
	        return ResponseEntity.ok(response);
	    }

	    @Operation(
	    		summary="To Get All Details of specific student",
	    		description="To Get All Details of specific student"
	    		)
	    @GetMapping("/{id}")
	    public ResponseEntity<StudentResponseDTO> getStudent(@PathVariable int id) {
	        StudentResponseDTO response = studentService.getStudentById(id);
	        return ResponseEntity.ok(response);
	    }
	    
	    
	    @Operation(
	    		summary="To Add all the Student data",
	    		description="To Add all the Student data"
	    		)   
	@PostMapping("/Add")
    public ResponseEntity<String> addStudent(@RequestBody Gd_Student student) {
        try {
            String result = studentService.addStudent(student);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }
	
	    @Operation(
	    		summary="To manage the Updations",
	    		description="To Manage 1.Update Operation 2.Activate Operation 3.Deactivate Operation"
	    		)
	@PutMapping("/manage/{studentId}")
	public ResponseEntity<?> manageStudent(
	        @PathVariable Integer studentId,
	        @RequestBody (required=false)Gd_Student student,
	        @RequestParam String action) {

	    Object result = studentService.manageStudent(studentId, student, action);
	    return ResponseEntity.ok(result);
	}
	    
	    

	 @Operation(
			 summary="To Delete Record",
	    		description="To Delete Record"
			 )
	@DeleteMapping("/Delete/{id}")
	public ResponseEntity<String> deleteStudent(@PathVariable int id) {
	    try {
	        String message = studentService.deleteStudentById(id);
	        return ResponseEntity.ok(message);
	    } catch (NoSuchElementException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the student.");
	    }
	}
}