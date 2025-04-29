package com.example.student.controller;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.student.DTO.StudentDTO;
import com.example.student.Service.StudentService;
import com.example.student.entity.Gd_Student;


@RestController
@RequestMapping("/Student")
public class StudentController {
	    
	    @Autowired
	    private StudentService studentService;
	
	    @GetMapping("/All")
	    public ResponseEntity<List<StudentDTO>> getAllStudents() {
	        List<StudentDTO> students = studentService.getAllStudents();
	        return ResponseEntity.ok(students);
	    }

	@GetMapping("/{id}")
	public ResponseEntity<StudentDTO> getStudentById(@PathVariable int id) {
	    try {
	        StudentDTO studentDTO = studentService.getStudentById(id);
	        return ResponseEntity.ok(studentDTO);
	    } catch (NoSuchElementException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    }
	}
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
	@PutMapping("/update/{id}")
    public ResponseEntity<String> updateStudent(@PathVariable Integer id, @RequestBody Gd_Student student) {
        try {
            // Update the student record
            Gd_Student updatedStudent = studentService.updateStudent(id, student);
            return ResponseEntity.ok("Student updated successfully: " + updatedStudent.getNAME());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
	
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