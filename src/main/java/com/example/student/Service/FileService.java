package com.example.student.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.student.entity.Gd_FileSubmission;
import com.example.student.entity.User;
import com.example.student.repository.FilesubmissionRepository;
import com.example.student.repository.UserRepository;


@Service
public class FileService {
	
	@Autowired
    private UserRepository userRepository;
	
	 @Autowired
	    private FilesubmissionRepository repository;

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

}
