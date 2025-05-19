package com.example.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.student.DTO.TeacherRegistarationDTO;
import com.example.student.Service.TeacherService;




@RestController
public class TeacherController {
	
	 @Autowired
	    private TeacherService teacherService;

	 @PostMapping("/teacher/register")
	    public ResponseEntity<String> registerTeacher(@RequestBody TeacherRegistarationDTO dto) {
	        // Validating incoming request
	        if (dto.getName() == null || dto.getEmail() == null || dto.getPassword() == null) {
	        	System.out.println(dto);
	        	System.out.println("Email:"+dto.getEmail()); 
	            return ResponseEntity.badRequest().body("Teacher name, email, or password is missing.");
	        }

	        // Call service to add the teacher and user
	        String result = teacherService.addTeacher(dto);
	        return ResponseEntity.ok(result); // Successful registration
	    }


}
