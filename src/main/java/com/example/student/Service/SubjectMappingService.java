package com.example.student.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.student.DTO.PageSortDTO;
import com.example.student.DTO.SubjectMappingDTO;
import com.example.student.DTO.SubjectMappingRequestDTO;
import com.example.student.entity.Gd_Subject_Mapping;
import com.example.student.repository.ClassRepository;
import com.example.student.repository.SubjectMappingRepository;
import com.example.student.repository.SubjectRepository;

@Service
public class SubjectMappingService {
	
	
	@Autowired
    private SubjectMappingRepository subjectMappingRepository;

    @Autowired
    private ClassRepository gdClassRepository;

    @Autowired
    private SubjectRepository gdSubjectRepository;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public PageSortDTO<SubjectMappingDTO> getAllSubjectMappings(Integer classId, Integer subjectId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        
        Page<Gd_Subject_Mapping> mappingsPage = subjectMappingRepository
            .findAllWithFilter(classId, subjectId, pageable);

        List<SubjectMappingDTO> content = mappingsPage.stream().map(mapping -> {
            SubjectMappingDTO dto = new SubjectMappingDTO();
            dto.setClassId(mapping.getGd_class().getCLASS_ID());
            dto.setClassName(mapping.getGd_class().getCLASS_NAME());
            dto.setSubjectId(mapping.getGd_subject().getSUBJECT_ID());
            dto.setSubjectName(mapping.getGd_subject().getSUBJECT_NAME());
            return dto;
        }).toList(); // Or use .collect(Collectors.toList());

        PageSortDTO.PaginationDetails pagination = new PageSortDTO.PaginationDetails(
            mappingsPage.getNumber(),           // pageNumber (0-based)
            mappingsPage.getTotalPages(),       // totalPages
            (int) mappingsPage.getTotalElements() // totalElements as int
        );

        return new PageSortDTO<>(content, pagination);
    }


    public SubjectMappingDTO getSubjectMappingDTOById(int id) {
        Optional<Gd_Subject_Mapping> optional = subjectMappingRepository.findById(id);
        return optional.map(this::convertToDTO).orElse(null);
    }

    public Gd_Subject_Mapping saveSubjectMapping(SubjectMappingRequestDTO requestDTO) {
        Gd_Subject_Mapping subjectMapping = new Gd_Subject_Mapping();
        Integer classId=requestDTO.getClassId();
        Integer subjectId=requestDTO.getSubjectId();
        System.out.println(classId+" "+subjectId);
        if(classId==0|| subjectId==0) {
        	throw new IllegalArgumentException("Both classId and subjectId must be provided.");
        }
      
        String checkQuery = "SELECT COUNT(*) FROM GD_SUBJECT_MAPPTING WHERE CLASS_ID = ? AND SUBJECT_ID = ?";
        int existingCount = jdbcTemplate.queryForObject(checkQuery, Integer.class, classId, subjectId);

        if (existingCount > 0) {
            throw new IllegalArgumentException("Mapping already exists for the given classId and subjectId.");
        }
        subjectMapping.setGd_class(gdClassRepository.findById(requestDTO.getClassId()).orElse(null));
        subjectMapping.setGd_subject(gdSubjectRepository.findById(requestDTO.getSubjectId()).orElse(null));
        return subjectMappingRepository.save(subjectMapping);
    }
    
    private SubjectMappingDTO convertToDTO(Gd_Subject_Mapping mapping) {
        SubjectMappingDTO dto = new SubjectMappingDTO();
        dto.setClassId(mapping.getGd_class().getCLASS_ID());
        dto.setClassName(mapping.getGd_class().getCLASS_NAME());
        dto.setSubjectId(mapping.getGd_subject().getSUBJECT_ID());
        dto.setSubjectName(mapping.getGd_subject().getSUBJECT_NAME());
        return dto;
    }


	
}
