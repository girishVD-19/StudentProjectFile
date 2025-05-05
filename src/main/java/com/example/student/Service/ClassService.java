package com.example.student.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.student.DTO.ClassDetailsDTO;
import com.example.student.DTO.ClassDetailsDTO.SubjectDTOS;
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



	public List<ClassDetailsDTO> getAllClassDetails() {
	    // Fetch all class details with room and subjects
	    List<Object[]> results = classrepository.findAllClassDetailsWithRoomAndSubjects();


	    HashMap<Integer, ClassDetailsDTO> classDetailsMap = new HashMap<>();

	    for (Object[] result : results) {
	        Integer classId = (Integer) result[0];

	        // If we haven't created DTO for this class yet, create and store it
	        ClassDetailsDTO dto = classDetailsMap.get(classId);
	        if (dto == null) {
	            dto = new ClassDetailsDTO();
	            dto.setClassId(classId);
	            dto.setClassName((String) result[1]);
	            dto.setStd((String) result[2]);
	            dto.setRoomId((Integer) result[3]);
	            dto.setRoomCapacity((Integer) result[4]);
	            dto.setSubjects(new ArrayList<>());
	            classDetailsMap.put(classId, dto);
	        }

	        // Add subject to the class's subject list
	        SubjectDTOS subjectDTO = new SubjectDTOS();
	        subjectDTO.setId((Integer) result[5]);
	        subjectDTO.setName((String) result[6]);
	        dto.getSubjects().add(subjectDTO);
	    }

	    return new ArrayList<>(classDetailsMap.values());
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
