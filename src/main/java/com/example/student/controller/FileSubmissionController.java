package com.example.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.student.Service.FileService;
import com.example.student.entity.Gd_FileSubmission;

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
	
	@PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/download/{fileId}")
    public ResponseEntity<?> downloadFile(@PathVariable Integer fileId) {
        Gd_FileSubmission file = fileservice.getFile(fileId);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + file.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(file.getFilePath()))
                .body(new ByteArrayResource(file.getFileData()));
    }
	
	
	

}
