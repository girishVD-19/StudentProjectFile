package com.example.student.controller;

import java.util.List;

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

import com.example.student.Service.SubjectService;
import com.example.student.entity.Gd_Subject;

@RestController
@RequestMapping("Subject")
public class SubjectController {
	
	@Autowired
	private SubjectService subjectservice;
	
	 @PostMapping("Add")
	    public ResponseEntity<Gd_Subject> createSubject(@RequestBody Gd_Subject subject) {
	        return ResponseEntity.ok(subjectservice.saveSubject(subject));
	    }

	    @GetMapping("All")
	    public ResponseEntity<List<Gd_Subject>> getAllSubjects() {
	        return ResponseEntity.ok(subjectservice.getAllSubjects());
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Gd_Subject> getSubjectById(@PathVariable int id) {
	        return subjectservice.getSubjectById(id)
	                .map(ResponseEntity::ok)
	                .orElse(ResponseEntity.notFound().build());
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<Gd_Subject> updateSubject(@PathVariable int id, @RequestBody Gd_Subject updatedSubject) {
	        return subjectservice.updateSubject(id, updatedSubject)
	                .map(ResponseEntity::ok)
	                .orElse(ResponseEntity.notFound().build());
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteSubject(@PathVariable int id) {
	        boolean deleted = subjectservice.deleteSubject(id);
	        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
	    }
	

}
