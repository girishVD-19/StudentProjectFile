package com.example.student.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.student.DTO.ClassDetailsDTO;
import com.example.student.DTO.ClassDetailsDTO.SubjectDTOS;
import com.example.student.DTO.ClassResponseDTO;
import com.example.student.DTO.ClassWithStudentDTO;
import com.example.student.DTO.StudentListResponseDTO;
import com.example.student.DTO.SubjectDTO;
import com.example.student.entity.Gd_Class;
import com.example.student.entity.Gd_Rooms;
import com.example.student.entity.Gd_Subject_Mapping;
import com.example.student.repository.ClassRepository;
import com.example.student.repository.RoomRepository;
import com.example.student.repository.SubjectMappingRepository;

@Service
public class ClassService {
	@Autowired
	private ClassRepository classrepository;
	
	
	@Autowired 
	private RoomRepository roomrepository;
	
	@Autowired
	private SubjectMappingRepository subjectmappingrepository;
	
	public String createGdClass(ClassDetailsDTO dto) {
	    Gd_Rooms room = roomrepository.findById(dto.getRoomId())
	        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room ID does not exist"));

	    Gd_Class gdClass = new Gd_Class();
	    gdClass.setCLASS_NAME(dto.getClassName());
	    gdClass.setSTD(dto.getStd());
	    gdClass.setGd_roooms(room);

	    Gd_Class saved = classrepository.save(gdClass);
	    return "Class created with ID: " + saved.getCLASS_ID();
	}
     
	
	public ClassWithStudentDTO getClassWithStudents(Integer classId) {
        Gd_Class gdClass = classrepository.findById(classId)
            .orElseThrow(() -> new IllegalArgumentException("Class not found with ID: " + classId));

        ClassWithStudentDTO dto = new ClassWithStudentDTO();
        dto.setClassId(gdClass.getCLASS_ID());
        dto.setClassName(gdClass.getCLASS_NAME());
        dto.setStd(gdClass.getSTD());

       
        // Map students
        List<ClassWithStudentDTO.StudentDTOS> studentDTOs = gdClass.getGd_student().stream()
            .map(student -> {
                ClassWithStudentDTO.StudentDTOS s = new ClassWithStudentDTO.StudentDTOS();
                s.setStudentId(student.getSTUDENT_ID());
                s.setStudentName(student.getNAME());
                s.setRollNo(student.getROLL_NO());
                return s;
            }).collect(Collectors.toList());

        dto.setStudent(studentDTOs);
        return dto;
    }
	public ClassResponseDTO getAllClassDetails(Pageable pageable) {
	    // Fetch paginated data from the repository
	    Page<Object[]> results = classrepository.findAllClassDetailsWithRoomAndSubjects(pageable);

	    // Convert the results into a List of ClassDetailsDTO
	    List<ClassDetailsDTO> classDetails = new ArrayList<>();

	    for (Object[] result : results) {
	        ClassDetailsDTO dto = new ClassDetailsDTO();
	        dto.setClassId((Integer) result[0]);
	        dto.setClassName((String) result[1]);
	        dto.setStd((String) result[2]);
	        dto.setRoomId((Integer) result[3]);
	        dto.setRoomCapacity((Integer) result[4]);

	        // Add subjects to the DTO
	        SubjectDTOS subjectDTO = new SubjectDTOS();
	        subjectDTO.setId((Integer) result[5]);
	        subjectDTO.setName((String) result[6]);
	        dto.setSubjects(Collections.singletonList(subjectDTO));

	        classDetails.add(dto);
	    }

	    // Create the response DTO with pagination metadata
	    ClassResponseDTO response = new ClassResponseDTO();
	    response.setContent(classDetails);
	    response.setTotalElements(results.getTotalElements());
	    response.setTotalPages(results.getTotalPages());
	    response.setPageNumber(results.getNumber()+1);
	    response.setPageSize(results.getSize());

	    return response;
	}


	

	    

	
	public ClassDetailsDTO getClassDetails(Integer classId) {
        // Fetch the class details with room and subjects from the repository
        List<Object[]> results = classrepository.findClassDetailsWithRoomAndSubjects(classId);
        
        
        // Initialize DTO
        ClassDetailsDTO dto = new ClassDetailsDTO();
        
        if(results.isEmpty()) {
        	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Class not found");
       }
        // If the query returns results, process them
        if (!results.isEmpty()) {
            Object[] firstResult = results.get(0); // Assumption: There is at least one result
            dto.setClassId((Integer) firstResult[0]);
            dto.setClassName((String) firstResult[1]);
            dto.setStd((String) firstResult[2]);
            dto.setRoomId((Integer) firstResult[3]);
            dto.setRoomCapacity((Integer) firstResult[4]);

            // Initialize subjects list
            List<SubjectDTOS> subjects = new ArrayList<>();
            for (Object[] result : results) {
                SubjectDTOS subjectDTO = new SubjectDTOS();
                subjectDTO.setId((Integer) result[5]);
                subjectDTO.setName((String) result[6]);
                subjects.add(subjectDTO);
            }
            dto.setSubjects(subjects);
        }

        return dto;
    }


    public ClassDetailsDTO updateGdClass(Integer id, ClassDetailsDTO dto, Integer roomId) {
        Gd_Class existing = classrepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Class not found"));

        Gd_Rooms room = roomrepository.findById(roomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room ID does not exist"));

        existing.setCLASS_NAME(dto.getClassName());
        existing.setSTD(dto.getStd());
        existing.setGd_roooms(room);

        Gd_Class updated = classrepository.save(existing);
        return new ClassDetailsDTO(updated.getCLASS_ID(), updated.getCLASS_NAME(), updated.getSTD());
    }

    public void deleteGdClass(Integer id) {
        if (!classrepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Class not found");
        }
        classrepository.deleteById(id);
    }
}
