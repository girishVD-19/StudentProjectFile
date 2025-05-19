package com.example.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.student.DTO.ReviewRequestDTO;
import com.example.student.Service.ReviewService;

@RestController
@RequestMapping("review")
public class ReviewController {
	
	@Autowired
	private ReviewService reviewservice;
	
	@PreAuthorize("hasRole('TEACHER')")
	@PostMapping("send")
    public ResponseEntity<?> submitReview(@RequestBody ReviewRequestDTO request) {
        reviewservice.submitReview(request);
        return ResponseEntity.ok("Review submitted successfully.");
    }

}
