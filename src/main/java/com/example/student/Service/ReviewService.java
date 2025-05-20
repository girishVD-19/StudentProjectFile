package com.example.student.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.student.DTO.ReviewRequestDTO;
import com.example.student.config.JwtHelper;
import com.example.student.entity.Gd_Class;
import com.example.student.entity.Gd_FileSubmission;
import com.example.student.entity.Gd_Review;
import com.example.student.entity.Gd_Student;
import com.example.student.entity.Gd_Teacher;
import com.example.student.entity.User;
import com.example.student.repository.ClassRepository;
import com.example.student.repository.FilesubmissionRepository;
import com.example.student.repository.ReviewRepository;
import com.example.student.repository.StudentRepository;
import com.example.student.repository.TeacherRepository;
import com.example.student.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ReviewService {
	 @Autowired
	    private ReviewRepository reviewrepository;

	    @Autowired
	    private FilesubmissionRepository filesubmissionrepository;

	    @Autowired
	    private UserRepository userrepository;
	    
	    @Autowired 
		 private TeacherRepository teacherrepository;
	    
	    @Autowired 
		 private StudentRepository studentrepository;
	    
	    private final JwtHelper jwtUtil;

	    @Autowired
	    public ReviewService(JwtHelper jwtHelper) {
	        this.jwtUtil = jwtHelper;
	    }

	    public void submitReview(ReviewRequestDTO request, HttpServletRequest httpRequest) {
	    	
	        String token = jwtUtil.getTokenFromRequest(httpRequest); 
	        

	        User reviewer = userrepository
	                .findById(request.getReviewerId())
	                .orElseThrow(() -> new RuntimeException("Reviewer not found"));
	        
	        
	        if (token == null) {
	            throw new RuntimeException("Token not provided");
	        }

	        Integer userIdFromToken = jwtUtil.extractUserId(token);
	        System.out.println("User ID from Token: " + userIdFromToken);
	        System.out.println("reviewer iD:"+request.getReviewerId());

	        // Step 3: Validate that the reviewerId matches the userId from the token
	        if (userIdFromToken!=request.getReviewerId()){
	            throw new RuntimeException("Reviewer ID does not match the authenticated user");
	        }
	        
	        
	        Gd_FileSubmission submission = filesubmissionrepository
	                .findById(request.getFileSubmissionId())
	                .orElseThrow(() -> new RuntimeException("Submission not found"));
	        
	       Integer Uploader=submission.getUploader().getUserId();
	       System.out.println("UploaderId:"+Uploader);

	      
	     // Fetch uploader's class
	        Gd_Student student = studentrepository.findById(Uploader)
	                .orElseThrow(() -> new RuntimeException("Uploader (student) not found"));

	        Gd_Class studentClass = student.getGd_class();
	        Integer studentClassId = studentClass.getCLASS_ID();
	        
	        System.out.println("studentclass:"+studentClassId);
	       

	        // Fetch reviewer's class
	        Gd_Teacher teacher = teacherrepository.findById(request.getReviewerId())
	                .orElseThrow(() -> new RuntimeException("Reviewer (teacher) not found"));

	        List<Gd_Class> teacherClasses = teacher.getClasses();
	        

	        // Check if any teacher class matches student class
	        boolean canReview = teacherClasses.stream()
	                .anyMatch(cls -> cls.getCLASS_ID().equals(studentClassId));

	        if (!canReview) {
	            throw new IllegalArgumentException("Not Have Permission to review");
	        }
	        
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
