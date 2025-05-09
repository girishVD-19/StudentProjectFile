package com.example.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	
	@Operation(summary="To Add the Student Details",
			description="To Add the Student Details")
	@PostMapping("/Add")
	public Gd_Subject createOrUpdateSubject(@RequestBody Gd_Subject subject) {
		return subjectrepository.save(subject);
	}

	@Operation(summary="To get all the Student Details",
			description="To get all the Student Details")
	    @GetMapping("/")
	    public ResponseEntity<List<Gd_Subject>> getAllSubjects() {
	        return ResponseEntity.ok(subjectservice.getAllSubjects());
	    }
 
	@Operation(summary="To get the Student Details by Id",
			description="To get the Student Details by Id")
	    @GetMapping("/{id}")
	    public ResponseEntity<Gd_Subject> getSubjectById(@PathVariable int id) {
	        return subjectservice.getSubjectById(id)
	                .map(ResponseEntity::ok)
	                .orElse(ResponseEntity.notFound().build());
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
