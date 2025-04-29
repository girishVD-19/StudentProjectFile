package com.example.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.student.DTO.MarkDTO;
import com.example.student.Service.StudentMarkService;
import com.example.student.entity.Gd_Student_Mark;

@RestController
@RequestMapping("Mark")
public class MarkController {
	
	 @Autowired
	    private StudentMarkService markService;
	 
    @PostMapping("/Add")
    public ResponseEntity<Gd_Student_Mark> createMark(@RequestBody Gd_Student_Mark mark) {
        Gd_Student_Mark saved = markService.saveStudentMark(mark);
        return ResponseEntity.ok(saved);
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMark(@PathVariable int id) {
        markService.deleteMarkById(id);
        return ResponseEntity.noContent().build();
    }

}
