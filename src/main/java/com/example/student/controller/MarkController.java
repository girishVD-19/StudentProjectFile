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
import com.example.student.DTO.StudentMarkDTO;
import com.example.student.Service.StudentMarkService;
import com.example.student.entity.Gd_Student_Mark;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("Mark")
public class MarkController {
	
	 @Autowired
	    private StudentMarkService markService;
	 
	 @GetMapping("/all")
	 @Operation(
			 summary="To Get All Marks of Table",
			 description="To Get All Marks of Table"
			 )
	    public ResponseEntity<List<MarkDTO>> getAllMarks() {
	        List<MarkDTO> marks = markService.getAllMarks();
	        return ResponseEntity.ok(marks);
	    }
	 
	 @PostMapping("/Add")
	 @Operation(
			 summary="To Get All Marks of Table",
			 description="To Get All Marks of Table"
			 )
	    public ResponseEntity<String> createMark(@RequestBody Gd_Student_Mark inputMark) {
	        try {
	            markService.saveStudentMark(inputMark);
	            return ResponseEntity.ok("Student mark saved successfully.");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("Error saving student mark: " + e.getMessage());
	        }
	    }


    @GetMapping("/{id}")
    @Operation(
			 summary="To Get Mark detail of specific mark id",
			 description="To Get Mark detail of specific mark id"
			 )
    public ResponseEntity<MarkDTO> getMarkById(@PathVariable int id) {
        MarkDTO mark = markService.getMarkById(id);
        if (mark == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mark);
    }
    
    @GetMapping("/student/{studentId}")
    @Operation(
			 summary="To Get The All Mark Data of student",
			 description="To Get The All Mark Data of student"
			 )
    public ResponseEntity<List<MarkDTO>> getMarksByStudentId(@PathVariable int studentId) {
        List<MarkDTO> marks = markService.getMarksByStudentId(studentId);
        if (marks.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(marks);
    }
    
    @Operation(
			 summary="To Get The Mark Data of student By ClassId",
			 description="To Get The Mark Data of student By ClassId"
			 )
    @GetMapping("student/{studentId}/class/{classId}")
    public ResponseEntity<List<MarkDTO>> getMarksByStudentIdAndClassId(
            @PathVariable int studentId, @PathVariable int classId) {
        
        List<MarkDTO> marks = markService.getMarksByStudentIdAndClassId(studentId, classId);
        
        if (marks.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(marks);
    }
    @Operation(
			 summary="To Get The Mark Data of student By Serial No",
			 description="To Get The Mark Data of student By Serial No"
			 )
    @GetMapping("/{studentId}/{srNo}")
    public List<StudentMarkDTO> getStudentMarks(@PathVariable int studentId, @PathVariable int srNo) {
        return markService.getStudentMarks(studentId, srNo);
    }
    
    @DeleteMapping("/{id}")
    @Operation(
    		summary="To Delete The Mark Id",
    		description="To Delete The Mark Id"
    		)
    public ResponseEntity<Void> deleteMark(@PathVariable int id) {
        markService.deleteMarkById(id);
        return ResponseEntity.noContent().build();
    }
}
