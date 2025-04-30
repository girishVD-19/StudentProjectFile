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

import com.example.student.DTO.SubjectMappingDTO;
import com.example.student.Service.SubjectMappingService;
import com.example.student.entity.Gd_Subject_Mapping;

@RestController
@RequestMapping("SubjectMap")
public class StudentMappingController {
	
	 @Autowired
	    private SubjectMappingService service;

	   
	    @GetMapping("/All")
	    public List<SubjectMappingDTO> getAllMappings() {
	        return service.getAllMappings(); // This must return List<SubjectMappingDTO>
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<SubjectMappingDTO> getById(@PathVariable int id) {
	        SubjectMappingDTO dto = service.getMappingById(id);
	        return ResponseEntity.ok(dto);
	    }

	    
	    

	    @PostMapping("Add")
	    public Gd_Subject_Mapping createMapping(@RequestBody Gd_Subject_Mapping mapping) {
	        return service.saveMapping(mapping);
	    }

	    @DeleteMapping("Delete/{id}")
	    public void deleteMapping(@PathVariable int id) {
	        service.deleteMappingById(id);
	    }

}
