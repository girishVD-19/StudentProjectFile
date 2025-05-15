package com.example.student.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.student.DTO.PageSortDTO;
import com.example.student.DTO.SubjectWithClassDTO;
import com.example.student.DTO.SubjectWithClassDTO.ClassDTO;
import com.example.student.entity.Gd_Subject;
import com.example.student.entity.Gd_Subject_Mapping;
import com.example.student.repository.SubjectRepository;

@Service
public class SubjectService {
	
	// Create
	
	@Autowired
	private SubjectRepository subjectrepository;
	
	@Autowired 
	private JdbcTemplate jdbctemplete;

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
	public SubjectWithClassDTO getSubjectWithClasses(int subjectId) {
	    Optional<Gd_Subject> subjectOpt = subjectrepository.findById(subjectId);

	    if (subjectOpt.isEmpty()) {
	        throw new RuntimeException("Subject not found with id: " + subjectId);
	    }

	    Gd_Subject subject = subjectOpt.get();

	    List<ClassDTO> classDTOs = subject.getGd_subject_mapping().stream()
	        .map(Gd_Subject_Mapping::getGd_class)
	        .filter(Objects::nonNull)
	        .map(gdClass -> new ClassDTO(gdClass.getCLASS_ID(), gdClass.getCLASS_NAME(),gdClass.getSTD()))
	        .collect(Collectors.toList());
	    

	    SubjectWithClassDTO dto = new SubjectWithClassDTO();
	    dto.setSubjectId(subject.getSUBJECT_ID());
	    dto.setSubjectName(subject.getSUBJECT_NAME());
	    dto.setClasses(classDTOs);

	    return dto;
	}
	 

	    public Gd_Subject createSubject(Gd_Subject subject) {
	        String subjectName = subject.getSUBJECT_NAME();

	        String query = "SELECT COUNT(*) FROM GD_SUBJECT WHERE SUBJECT_NAME = ?";
	        Integer count = jdbctemplete.queryForObject(query, Integer.class, subjectName);

	        if (count != null && count > 0) {
	            throw new IllegalArgumentException("Subject with name '" + subjectName + "' already exists.");
	        }

	        return subjectrepository.save(subject);
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
