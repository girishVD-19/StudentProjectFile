package com.example.student.Service;

import java.io.IOException;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.student.entity.Gd_Class;
import com.example.student.entity.Gd_FileSubmission;
import com.example.student.entity.Gd_Student;
import com.example.student.entity.Gd_Teacher;
import com.example.student.entity.User;
import com.example.student.repository.FilesubmissionRepository;
import com.example.student.repository.StudentRepository;
import com.example.student.repository.TeacherRepository;
import com.example.student.repository.UserRepository;


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
    
    public ResponseEntity<Resource> downloadFile(Integer fileId, User currentUser) throws AccessDeniedException {
        Gd_FileSubmission file = repository.findById(fileId)
            .orElseThrow(() -> new RuntimeException("File not found"));

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
        if (currentUser.getRoles().equals("PRINCIPAL")) {
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



}
