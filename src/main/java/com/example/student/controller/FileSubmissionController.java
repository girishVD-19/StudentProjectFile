package com.example.student.controller;

import java.nio.file.AccessDeniedException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.student.Service.FileService;

import com.example.student.entity.User;

@RestController
@RequestMapping("file")
public class FileSubmissionController {
	@Autowired
	private FileService fileservice;
	
	@PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("uploaderId") Integer uploaderId) {
        try {
            String message = fileservice.uploadFile(file, uploaderId);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file: " + e.getMessage());
        }
    }
	 @GetMapping("/download/{fileId}")
	    public ResponseEntity<Resource> downloadFile(@PathVariable Integer fileId, Principal principal) throws AccessDeniedException {
	        User currentUser = getUserFromPrincipal(principal); // You can implement this method to get the user from the principal object
	        
	        return fileservice.downloadFile(fileId, currentUser); // Delegating to the service layer
	    }
	 private User getUserFromPrincipal(Principal principal) {
	        return (User) ((Authentication) principal).getPrincipal();
	    }
}
