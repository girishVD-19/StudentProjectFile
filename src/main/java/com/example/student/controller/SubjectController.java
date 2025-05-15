package com.example.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.student.DTO.PageSortDTO;
import com.example.student.DTO.SubjectWithClassDTO;
import com.example.student.Service.SubjectService;
import com.example.student.entity.Gd_Subject;
import com.example.student.repository.SubjectRepository;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("Subject")
public class SubjectController {
	
	@Autowired
	private SubjectService subjectservice;
	@Autowired
	private SubjectRepository subjectrepository;
	
	@Autowired
	private JdbcTemplate jdbctemplate;
	
	
	@Operation(summary="To Add the Student Details",
			description="To Add the Student Details")
	@PostMapping("/add")
    public ResponseEntity<Gd_Subject> createSubject(@RequestBody Gd_Subject subject) {
        Gd_Subject savedSubject = subjectservice.createSubject(subject);
        return ResponseEntity.ok(savedSubject);
    }
	@Operation(summary="To get all the Student Details",
			description="To get all the Student Details")
	@GetMapping("/")
	public ResponseEntity<PageSortDTO<Gd_Subject>> getAllSubjects(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size) {

	    Pageable pageable = PageRequest.of(page, size);
	    PageSortDTO<Gd_Subject> response = subjectservice.getAllSubjects(pageable);
	    return ResponseEntity.ok(response);
	}

	@Operation(summary="To get the Student Details by Id",
			description="To get the Student Details by Id")
	    @GetMapping("/{id}")
	    public SubjectWithClassDTO getSubjectById(@PathVariable int id) {
	        return subjectservice.getSubjectWithClasses(id);
	                
	    }

	    @Operation(summary="To update the Student Details by Id",
				description="To update  the Student Details by Id")
	    @PatchMapping("update/{id}")
	    public ResponseEntity<Gd_Subject> updateSubject(@PathVariable int id, @RequestBody Gd_Subject updatedSubject) {
	        return subjectservice.updateSubject(id, updatedSubject)
	                .map(ResponseEntity::ok)
	                .orElse(ResponseEntity.notFound().build());
	    }
	    
	    @Operation(summary="To delete the Student Details by Id",
				description="To delete the Student Details by Id")
	    @DeleteMapping("delete/{id}")
	    public ResponseEntity<Void> deleteSubject(@PathVariable int id) {
	        boolean deleted = subjectservice.deleteSubject(id);
	        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
	    }
}
