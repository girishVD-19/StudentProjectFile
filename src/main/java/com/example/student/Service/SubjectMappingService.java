package com.example.student.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<SubjectMappingDTO> getAllSubjectMappings() {
        List<Gd_Subject_Mapping> mappings = subjectMappingRepository.findAll();
        return mappings.stream().map(mapping -> {
            SubjectMappingDTO dto = new SubjectMappingDTO();
            dto.setClassId(mapping.getGd_class().getCLASS_ID());
            dto.setClassName(mapping.getGd_class().getCLASS_NAME());
            dto.setSubjectId(mapping.getGd_subject().getSUBJECT_ID());
            dto.setSubjectName(mapping.getGd_subject().getSUBJECT_NAME());
            return dto;
        }).collect(Collectors.toList());
    }

    public SubjectMappingDTO getSubjectMappingDTOById(int id) {
        Optional<Gd_Subject_Mapping> optional = subjectMappingRepository.findById(id);
        return optional.map(this::convertToDTO).orElse(null);
    }

    public Gd_Subject_Mapping saveSubjectMapping(SubjectMappingRequestDTO requestDTO) {
        Gd_Subject_Mapping subjectMapping = new Gd_Subject_Mapping();
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
