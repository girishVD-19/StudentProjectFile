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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo5.DTO.StudentDTO;
import com.example.student.DTO.ClassDetailsDTO;
import com.example.student.DTO.ClassResponseDTO;
import com.example.student.DTO.ClassSummary;
import com.example.student.DTO.ClassWithStudentDTO;
import com.example.student.DTO.PageSortDTO;
import com.example.student.DTO.PageSortDTO.PaginationDetails;
import com.example.student.DTO.RoomDTO;
import com.example.student.DTO.StudentForClass;
import com.example.student.DTO.StudentListResponseDTO;
import com.example.student.DTO.SubjectDTO;
import com.example.student.entity.Gd_Class;
import com.example.student.entity.Gd_Rooms;
import com.example.student.entity.Gd_Subject_Mapping;
import com.example.student.repository.ClassRepository;
import com.example.student.repository.RoomRepository;
import com.example.student.repository.SubjectMappingRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClassService {
	@Autowired
	private ClassRepository classrepository;
	
	
	@Autowired 
	private RoomRepository roomrepository;
	
	@Autowired
	private SubjectMappingRepository subjectmappingrepository;
	
	 public Gd_Class saveClass(Gd_Class gdClass) {
	        if (gdClass.getGd_roooms() != null) {
	            Integer roomId = gdClass.getGd_roooms().getRoom_Id();
	            Gd_Rooms room = roomrepository.findById(roomId)
	                    .orElseThrow(() -> new RuntimeException("Room with ID " + roomId + " not found"));
	            if(room.isIs_active()) {
	            	throw new RuntimeException("Room with ID " + roomId + " Already Asigned");
	            }
	            gdClass.setGd_roooms(room); // set the managed (persisted) room entity
	        }
	        return classrepository.save(gdClass);
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
        	    .filter(student -> student.isActive()) // or student.getIsActive() == true, depending on your method
        	    .map(student -> {
        	        ClassWithStudentDTO.StudentDTOS s = new ClassWithStudentDTO.StudentDTOS();
        	        s.setStudentId(student.getSTUDENT_ID());
        	        s.setStudentName(student.getNAME());
        	        s.setRollNo(student.getROLL_NO());
        	        return s;
        	    })
        	    .collect(Collectors.toList());


        dto.setStudent(studentDTOs);
        return dto;
    }
	 public Page<ClassDetailsDTO> getClassDetailsWithRoom(String std, Pageable pageable) {
	        return classrepository.findClassDetailsWithRoom(std, pageable);
	    }
	 
	 public Page<ClassDetailsDTO> getAllClassDetails(Pageable pageable) {
		    return classrepository.findAllClassDetails(pageable);
		}
	 
	 public PageSortDTO<ClassDetailsDTO> convertToPageSortDTO(Page<ClassDetailsDTO> page) {
		    List<ClassDetailsDTO> content = page.getContent();
		    PageSortDTO.PaginationDetails paginationDetails = new PageSortDTO.PaginationDetails(
		            page.getNumber(),
		            page.getTotalPages(),
		            (int) page.getTotalElements()
		    );
		    return new PageSortDTO<>(content, paginationDetails);
		}


	public ClassSummary getClassDetailsById(Integer classId) {
	    List<Object[]> rows = classrepository.findClassWithRelationsNative(classId);

	    if (rows.isEmpty()) {
	        throw new EntityNotFoundException("Class not found with ID: " + classId);
	    }

	    Object[] row = rows.get(0);

	    // Core class details
	    Integer classIdVal = (Integer) row[0];
	    String className = (String) row[1];
	    String std = (String) row[2];

	    // Room
	    Integer roomId = (Integer) row[3];
	    Integer roomCapacity = (Integer) row[4];
	    RoomDTO roomDTO = (roomId != null && roomCapacity != null) ? new RoomDTO(roomId, roomCapacity) : null;

	    // Aggregated subject and student details
	    String subjectIdsStr = (String) row[5];
	    String subjectNamesStr = (String) row[6];
	    String studentIdsStr = (String) row[7];
	    String studentNamesStr = (String) row[8];

	    Set<SubjectDTO> subjects = new HashSet<>();
	    Set<StudentForClass> students = new HashSet<>();

	    if (subjectIdsStr != null && subjectNamesStr != null) {
	        String[] subjectIds = subjectIdsStr.split(",");
	        String[] subjectNames = subjectNamesStr.split(",");
	        for (int i = 0; i < subjectIds.length; i++) {
	            subjects.add(new SubjectDTO(Integer.parseInt(subjectIds[i].trim()), subjectNames[i].trim()));
	        }
	    }

	    if (studentIdsStr != null && studentNamesStr != null) {
	        String[] studentIds = studentIdsStr.split(",");
	        String[] studentNames = studentNamesStr.split(",");
	        for (int i = 0; i < studentIds.length; i++) {
	            students.add(new StudentForClass(Integer.parseInt(studentIds[i].trim()), studentNames[i].trim()));
	        }
	    }

	    return new ClassSummary(classIdVal, className, std, roomDTO,
	                            new ArrayList<>(subjects), new ArrayList<>(students));
	}

	public Gd_Class updateClassRoom(Integer classId, Integer roomId) {
	    Gd_Class gdClass = classrepository.findById(classId)
	            .orElseThrow(() -> new RuntimeException("Class not found with id: " + classId));

	    Gd_Rooms room = roomrepository.findById(roomId)
	            .orElseThrow(() -> new RuntimeException("Room not found with id: " + roomId));
	    if (!room.isIs_active()) {
	        throw new IllegalStateException("Room is not active");
	    }


	    gdClass.setGd_roooms(room);
	    return classrepository.save(gdClass);
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
