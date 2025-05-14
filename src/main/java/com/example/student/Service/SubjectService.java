package com.example.student.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.student.DTO.PageSortDTO;
import com.example.student.entity.Gd_Subject;
import com.example.student.repository.SubjectRepository;

@Service
public class SubjectService {
	
	// Create
	
	@Autowired
	private SubjectRepository subjectrepository;

    // Read All
	public PageSortDTO<Gd_Subject> getAllSubjects(Pageable pageable) {
	    Page<Gd_Subject> subjectPage = subjectrepository.findAll(pageable);

	    PageSortDTO.PaginationDetails paginationDetails = new PageSortDTO.PaginationDetails(
	        subjectPage.getNumber() + 1,
	        subjectPage.getTotalPages(),
	        (int) subjectPage.getTotalElements()
	    );

	    return new PageSortDTO<>(subjectPage.getContent(), paginationDetails);
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
