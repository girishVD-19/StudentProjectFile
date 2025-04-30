package com.example.student.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.student.DTO.ClassDetailsDTO;
import com.example.student.entity.Gd_Class;
import com.example.student.entity.Gd_Rooms;
import com.example.student.repository.ClassRepository;
import com.example.student.repository.RoomRepository;

@Service
public class ClassService {
	@Autowired
	private ClassRepository classrepository;
	
	
	@Autowired 
	private RoomRepository roomrepository;
	
	public ClassDetailsDTO createGdClass(ClassDetailsDTO dto, Integer roomId) {
        Gd_Rooms room = roomrepository.findById(roomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room ID does not exist"));

        Gd_Class gdClass = new Gd_Class();
        gdClass.setCLASS_NAME(dto.getClassName());
        gdClass.setSTD(dto.getStd());
        gdClass.setGd_roooms(room);

        Gd_Class saved = classrepository.save(gdClass);
        return new ClassDetailsDTO(saved.getCLASS_ID(), saved.getCLASS_NAME(), saved.getSTD());
    }

	public List<ClassDetailsDTO> getAllGdClasses() {
	    return classrepository.findAll().stream()
	            .map(c -> new ClassDetailsDTO(
	                    c.getCLASS_ID(),
	                    c.getCLASS_NAME(),
	                    c.getSTD(),
	                    c.getGd_roooms().getRoom_Id(),
	                    c.getGd_roooms().getCapacity()))
	            .collect(Collectors.toList());
	}


	public ClassDetailsDTO getGdClass(Integer id) {
	    Gd_Class gdClass = classrepository.findById(id)
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Class not found"));

	    return new ClassDetailsDTO(
	            gdClass.getCLASS_ID(),
	            gdClass.getCLASS_NAME(),
	            gdClass.getSTD(),
	            gdClass.getGd_roooms().getRoom_Id(),
	            gdClass.getGd_roooms().getCapacity());
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
