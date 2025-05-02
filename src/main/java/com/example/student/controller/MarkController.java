package com.example.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.student.DTO.MarkDTO;
import com.example.student.DTO.StudentMarkSummaryDTO;
import com.example.student.Service.StudentMarkService;
import com.example.student.entity.Gd_Student_Mark;

@RestController
@RequestMapping("Mark")
public class MarkController {
	
	 @Autowired
	    private StudentMarkService markService;
	 
	 @PostMapping("/Add")
	    public ResponseEntity<String> createMark(@RequestBody Gd_Student_Mark inputMark) {
	        try {
	            markService.saveStudentMark(inputMark);
	            return ResponseEntity.ok("Student mark saved successfully.");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("Error saving student mark: " + e.getMessage());
	        }
	    }

    @GetMapping("/all")
    public ResponseEntity<List<MarkDTO>> getAllMarks() {
        // Call the service method to get all marks with necessary details
        List<MarkDTO> responses = markService.getAllMarks();

        // Return the response
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarkDTO> getMarkById(@PathVariable int id) {
        MarkDTO response = markService.getMarkById(id);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/Student/{studentId}")
    public ResponseEntity<List<StudentMarkSummaryDTO>> getStudentMarks(@PathVariable int studentId) {
        try {
            List<StudentMarkSummaryDTO> response = markService.getMarksByStudentId(studentId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMark(@PathVariable int id) {
        markService.deleteMarkById(id);
        return ResponseEntity.noContent().build();
    }

}
