package com.example.student.controller;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.student.DTO.SubjectMappingDTO;
import com.example.student.DTO.SubjectMappingRequestDTO;
import com.example.student.Service.SubjectMappingService;
import com.example.student.entity.Gd_Subject_Mapping;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("SubjectMap")
public class SubjectMappingController {
	@Autowired
    private SubjectMappingService subjectMappingService;

    // Get all subject mappings with className and subjectName
	@Operation(summary="To get all Subject mapping  Data",
			    description="To get all  Subject mapping Data"
			)
	
    @GetMapping("/")
    public ResponseEntity<List<SubjectMappingDTO>> getAllSubjectMappings() {
        List<SubjectMappingDTO> mappings = subjectMappingService.getAllSubjectMappings();
        return new ResponseEntity<>(mappings, HttpStatus.OK);
    }

    // Get subject mapping by ID
    @GetMapping("/{id}")
    @Operation(summary="To get subject mapping of specific Id",
    description="To get subject mapping of specific Id"
)
    public ResponseEntity<SubjectMappingDTO> getMappingById(@PathVariable int id) {
        SubjectMappingDTO dto = subjectMappingService.getSubjectMappingDTOById(id);
        return dto != null ? new ResponseEntity<>(dto, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Save a new subject mapping (send classId and subjectId)
    @PostMapping("Add")
    @Operation(summary="Provid subject mappping",
    description="Provide Subject Mapping")
    public ResponseEntity<Gd_Subject_Mapping> saveSubjectMapping(@RequestBody SubjectMappingRequestDTO requestDTO) {
        Gd_Subject_Mapping savedMapping = subjectMappingService.saveSubjectMapping(requestDTO);
        return new ResponseEntity<>(savedMapping, HttpStatus.CREATED);
    } 
}