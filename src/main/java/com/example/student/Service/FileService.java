package com.example.student.Service;

import java.io.IOException;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.student.DTO.FileStatusDTO;
import com.example.student.DTO.FileStatusDTO.ReviewDetail;
import com.example.student.config.JwtHelper;
import com.example.student.entity.Gd_Class;
import com.example.student.entity.Gd_FileSubmission;
import com.example.student.entity.Gd_Review;
import com.example.student.entity.Gd_Student;
import com.example.student.entity.Gd_Teacher;
import com.example.student.entity.User;
import com.example.student.repository.ClassRepository;
import com.example.student.repository.FilesubmissionRepository;
import com.example.student.repository.StudentRepository;
import com.example.student.repository.TeacherRepository;
import com.example.student.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;


@Service
public class FileService {
	
	@Autowired
    private UserRepository userRepository;
	
	 @Autowired
	    private FilesubmissionRepository repository;
	 
	 @Autowired
	 private TeacherRepository teacherrepository;
	 
	 @Autowired
	 private StudentRepository studentrepository;
	 
	 private final JwtHelper jwtUtil;

	    @Autowired
	    public FileService(JwtHelper jwtHelper) {
	        this.jwtUtil = jwtHelper;
	    }
	 

    public String uploadFile(MultipartFile file, Integer uploaderId) throws IOException {
        User uploader = userRepository.findById(uploaderId)
                .orElseThrow(() -> new RuntimeException("Uploader not found"));

        Gd_FileSubmission submission = new Gd_FileSubmission(
                file.getOriginalFilename(),
                file.getContentType(),
                file.getBytes(),
                false,
                uploader,
                LocalDateTime.now()
        );

        repository.save(submission);
        return "File uploaded successfully.";
    }
    
    

    public Gd_FileSubmission getFile(Integer fileId) {
        return repository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found with ID: " + fileId));
    }
    public List<Gd_FileSubmission> getFilesByUploader(Integer uploaderId) {
        return repository.findByUploaderUserId(uploaderId);
    }
    
    
    
    public ResponseEntity<Resource> downloadFile(Integer fileId, User currentUser,HttpServletRequest httpRequest) throws AccessDeniedException {
        Gd_FileSubmission file = repository.findById(fileId)
            .orElseThrow(() -> new RuntimeException("File not found"));
        
        String token = jwtUtil.getTokenFromRequest(httpRequest); 
        if (token == null) {
            throw new RuntimeException("Token not provided");
        }

        Integer userIdFromToken = jwtUtil.extractUserId(token);
        System.out.println("User ID from Token: " + userIdFromToken);
        
       if(currentUser.getUserId()!=userIdFromToken) {
    	   throw new RuntimeException("The CurrentUser Not matched");
       }
        

        User uploader = file.getUploader();
        System.out.println("User;"+currentUser.getUserId()+currentUser.getRoles());

        // Rule 1: If the current user is the uploader (student)
        if (uploader.getUserId().equals(currentUser.getUserId())) {
            return buildDownloadResponse(file);
        }
               

        // Rule 2: If the current user is a teacher and teaches the student's class
        if (currentUser.getRoles().contains("TEACHER")) {
            Optional<Gd_Teacher> teacherOpt = teacherrepository.findById(currentUser.getUserId());
            Optional<Gd_Student> studentUploaderOpt = studentrepository.findById(uploader.getUserId());
            
            System.out.println("Class:"+teacherOpt.getClass());
            

            if (teacherOpt.isPresent() && studentUploaderOpt.isPresent()) {
                Gd_Teacher teacher = teacherOpt.get();
                Gd_Student studentUploader = studentUploaderOpt.get();

                // Check if the teacher is teaching the student's class
                List<Gd_Class> teacherClasses = teacher.getClasses(); // Get all classes the teacher teaches
                for (Gd_Class teacherClass : teacherClasses) {
                    // Check if the student's class is in the list of classes the teacher teaches
                    if (teacherClass.getGd_Student().contains(studentUploader)) {
                        return buildDownloadResponse(file);
                    }
                }
            }
        }

        // Rule 3: Principal has access to everything
        if (currentUser.getRoles().contains("PRINCIPAL")) {
            return buildDownloadResponse(file);
        }

        // If none of the rules are satisfied, deny access
        throw new AccessDeniedException("You do not have permission to access this file.");
    }

    
    private ResponseEntity<Resource> buildDownloadResponse(Gd_FileSubmission file) {
        ByteArrayResource resource = new ByteArrayResource(file.getFileData());

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
            .contentType(MediaType.parseMediaType(file.getFilePath()))
            .contentLength(file.getFileData().length)
            .body(resource);
    }
    
    	
    	 public FileStatusDTO getReviewStatusByFileId(Integer fileId,HttpServletRequest httpRequest) {
    		 
    		 String token = jwtUtil.getTokenFromRequest(httpRequest); 
    		 
    		 
 	        if (token == null) {
 	            throw new RuntimeException("Token not provided");
 	        }

 	        Integer userIdFromToken = jwtUtil.extractUserId(token);
 	        System.out.println("User ID from Token: " + userIdFromToken);
 	        
 	        
    		 
    	        Gd_FileSubmission submission = repository.findById(fileId)
    	                .orElseThrow(() -> new RuntimeException("File submission not found with ID: " + fileId));
    	        
    	        Integer AccessId=submission.getUploader().getUserId();
    	        System.out.println("Accessor Id:"+AccessId);
    	        
    	        if(AccessId!=userIdFromToken) {
    	        	throw new RuntimeException("Not Authenticated User");
    	        }
    	        
    	        
    	        

    	        List<ReviewDetail> reviewDetails = submission.getReviews().stream()
    	                .map(this::mapToReviewDetail)
    	                .collect(Collectors.toList());
    	        
    	        return new FileStatusDTO(
    	                submission.getFileName(),
    	                submission.getCreatedAt(),
    	                reviewDetails
    	        );
    	    }

    	    private ReviewDetail mapToReviewDetail(Gd_Review review) {
    	        return new ReviewDetail(
    	                review.getReviewer().getUserId(),
    	                review.getReviewer().getUsername(),
    	                review.getStatus().name(),
    	                review.getComment()
    	        );
    	    }
    
}
