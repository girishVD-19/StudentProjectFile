package com.example.student.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@Controller
@RequestMapping("/users")
public class UserController {
	 @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	    @GetMapping("/profile")
	    public ResponseEntity<String> getUserProfile() {
	        return ResponseEntity.ok("Welcome! This is your user profile.");
	    }
        
	 @PreAuthorize("hasRole('USER')")
	 @GetMapping("/all")
	 public ResponseEntity<String> getUser() {
	        return ResponseEntity.ok("Welcome! This is your user profile.");
	    }
	    
	    @GetMapping("/headers")
	    public ResponseEntity<?> getHeaders(@RequestHeader Map<String, String> headers) {
	        return ResponseEntity.ok(headers);
	    }
    
}
