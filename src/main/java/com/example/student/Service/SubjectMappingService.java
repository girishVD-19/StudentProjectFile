package com.example.student.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.student.DTO.ClassDetailsDTO;
import com.example.student.DTO.SubjectDTO;
import com.example.student.DTO.SubjectMappingDTO;
import com.example.student.entity.Gd_Class;
import com.example.student.entity.Gd_Subject;
import com.example.student.entity.Gd_Subject_Mapping;
import com.example.student.repository.ClassRepository;
import com.example.student.repository.SubjectMappingRepository;
import com.example.student.repository.SubjectRepository;

@Service
public class SubjectMappingService {
	
	@Autowired
    private SubjectMappingRepository mappingRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    public List<SubjectMappingDTO> getAllMappings() {
        List<Gd_Subject_Mapping> mappings = mappingRepository.findAll();
        List<SubjectMappingDTO> dtos = new ArrayList<>();

        for (Gd_Subject_Mapping mapping : mappings) {
            // Class info
        	 Gd_Class gdClass = mapping.getGd_class();
        	    ClassDetailsDTO classDTO = new ClassDetailsDTO(
        	        gdClass.getCLASS_ID(),
        	        gdClass.getCLASS_NAME(),
        	        gdClass.getSTD()
        	    );

            // Subject info
            Gd_Subject gdSubject = mapping.getGd_subject();
            SubjectDTO subjectDTO = new SubjectDTO(
                gdSubject.getSUBJECT_ID(),          // assumes getId() exists
                gdSubject.getSUBJECT_NAME()         // assumes getName() exists
            );

            // Build DTO
            SubjectMappingDTO dto = new SubjectMappingDTO(
                mapping.getSR_NO(),
                classDTO,
                subjectDTO
            );

            dtos.add(dto);
        }

        return dtos;
    }


    public Gd_Subject_Mapping saveMapping(Gd_Subject_Mapping mapping) {
        int classId = mapping.getGd_class().getCLASS_ID();  
        int subjectId = mapping.getGd_subject().getSUBJECT_ID();

        Gd_Class gdClass = classRepository.findById(classId).orElse(null);
        Gd_Subject gdSubject = subjectRepository.findById(subjectId).orElse(null);

        if (gdClass == null || gdSubject == null) {
            throw new IllegalArgumentException("Invalid class ID or subject ID");
        }

        mapping.setGd_class(gdClass);
        mapping.setGd_subject(gdSubject);

        return mappingRepository.save(mapping);
    }

    public SubjectMappingDTO getMappingById(int id) {
        Gd_Subject_Mapping mapping = mappingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Mapping not found with id: " + id));

        Gd_Class gdClass = mapping.getGd_class();
        ClassDetailsDTO classDTO = new ClassDetailsDTO(
            gdClass.getCLASS_ID(),
            gdClass.getCLASS_NAME(),
            gdClass.getSTD()
        );

        Gd_Subject gdSubject = mapping.getGd_subject();
        SubjectDTO subjectDTO = new SubjectDTO(
            gdSubject.getSUBJECT_ID(),
            gdSubject.getSUBJECT_NAME()
        );

        return new SubjectMappingDTO(mapping.getSR_NO(), classDTO, subjectDTO);
    }


    public void deleteMappingById(int id) {
        mappingRepository.deleteById(id);
    }
}
