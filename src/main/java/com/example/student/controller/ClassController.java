package com.example.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.student.DTO.ClassDetailsDTO;
import com.example.student.Service.ClassService;
import com.example.student.entity.Gd_Class;

@RestController
@RequestMapping("class")
public class ClassController {
	
	@Autowired
	private ClassService classservice;
	
	@GetMapping("All")
    public ResponseEntity<List<ClassDetailsDTO>> getAllClasses() {
        return ResponseEntity.ok(classservice.getAllGdClasses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassDetailsDTO> getClassById(@PathVariable Integer id) {
        return ResponseEntity.ok(classservice.getGdClass(id));
    }

    @PostMapping("Add")
    public ResponseEntity<ClassDetailsDTO> createClass(
            @RequestBody ClassDetailsDTO classDto
    ) {
        return new ResponseEntity<>(classservice.createGdClass(classDto, classDto.getRoomId()), HttpStatus.CREATED);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<ClassDetailsDTO> updateClass(
            @PathVariable Integer id,
            @RequestBody ClassDetailsDTO classDto,
            @RequestParam Integer roomId
    ) {
        return ResponseEntity.ok(classservice.updateGdClass(id, classDto, roomId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable Integer id) {
        classservice.deleteGdClass(id);
        return ResponseEntity.noContent().build();
    }

}
