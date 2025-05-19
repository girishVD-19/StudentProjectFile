package com.example.student.Service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.student.DTO.ReviewRequestDTO;
import com.example.student.entity.Gd_FileSubmission;
import com.example.student.entity.Gd_Review;
import com.example.student.entity.User;
import com.example.student.repository.FilesubmissionRepository;
import com.example.student.repository.ReviewRepository;
import com.example.student.repository.UserRepository;

@Service
public class ReviewService {
	 @Autowired
	    private ReviewRepository reviewrepository;

	    @Autowired
	    private FilesubmissionRepository filesubmissionrepository;

	    @Autowired
	    private UserRepository userrepository;

	    public void submitReview(ReviewRequestDTO request) {
	        Gd_FileSubmission submission = filesubmissionrepository
	                .findById(request.getFileSubmissionId())
	                .orElseThrow(() -> new RuntimeException("Submission not found"));

	        User reviewer = userrepository
	                .findById(request.getReviewerId())
	                .orElseThrow(() -> new RuntimeException("Reviewer not found"));

	        Gd_Review review = new Gd_Review(
	                submission,
	                reviewer,
	                request.getStatus(),
	                request.getComment(),
	                LocalDateTime.now()
	        );

	        reviewrepository.save(review);
	    }

}
