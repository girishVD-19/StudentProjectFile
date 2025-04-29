package com.example.student.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.student.entity.Gd_Subject;
import com.example.student.repository.SubjectRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SubjectService {
	
	// Create
	
	@Autowired
	private SubjectRepository subjectrepository;

    // Read All
    public List<Gd_Subject> getAllSubjects() {
        return subjectrepository.findAll();
    }

    // Read One
    public Optional<Gd_Subject> getSubjectById(int id) {
        return subjectrepository.findById(id);
    }

    // Update
    public Optional<Gd_Subject> updateSubject(int id, Gd_Subject updatedSubject) {
        return subjectrepository.findById(id).map(subject -> {
            subject.setSUBJECT_NAME(updatedSubject.getSUBJECT_NAME());
            return subjectrepository.save(subject);
        });
    }

    // Delete
    public boolean deleteSubject(int id) {
        if (subjectrepository.existsById(id)) {
            subjectrepository.deleteById(id);
            return true;
        }
        return false;

    }
}
