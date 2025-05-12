package com.example.student.Service;


import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.student.DTO.PageSortDTO;
import com.example.student.DTO.RoomWithClassDTO;
import com.example.student.entity.Gd_Class;
import com.example.student.entity.Gd_Rooms;
import com.example.student.repository.ClassRepository;
import com.example.student.repository.RoomRepository;

@Service
public class RoomsService {
	
	@Autowired
	private RoomRepository roomrepository;
	
	@Autowired
	private ClassRepository classrepository;
	public PageSortDTO<Gd_Rooms> getAllRooms(Pageable pageable) {
	    Page<Gd_Rooms> roomsPage = roomrepository.findAll(pageable);

	    // Create pagination details with only the three required fields
	    PageSortDTO.PaginationDetails paginationDetails = new PageSortDTO.PaginationDetails(
	            roomsPage.getPageable().getPageNumber(),
	            roomsPage.getTotalPages(),
	            (int) roomsPage.getTotalElements()
	    );

	    return new PageSortDTO<>(roomsPage.getContent(), paginationDetails);
	}


    // Get a room by its ID
    public Optional<Gd_Rooms> getRoomById(int roomId) {
        return roomrepository.findById(roomId);
    }

    // Create or update a room
    public Gd_Rooms createOrUpdateRoom(Gd_Rooms room) {
        return roomrepository.save(room);
    }

    public RoomWithClassDTO getRoomWithClass(Integer roomId) {
        List<Object[]> results = roomrepository.findRoomWithClassDetails(roomId);

        if (results.isEmpty()) {
            return null; // or throw custom exception
        }

        Object[] row = results.get(0); // Only expecting one class per room in this structure

        RoomWithClassDTO dto = new RoomWithClassDTO();
        dto.setRoom_id((Integer) row[0]);
        dto.setCapacity((Integer) row[1]);

        RoomWithClassDTO.ClassDTO classDTO = new RoomWithClassDTO.ClassDTO(
            (Integer) row[2],         // classId
            (String) row[3],          // className
            (String) row[4]           // std
        );

        dto.setClasses(classDTO);
        return dto;
    }
    
    public Gd_Class updateClassRoom(Integer classId, Integer roomId) {
	    Gd_Class gdClass = classrepository.findById(classId)
	            .orElseThrow(() -> new RuntimeException("Class not found with id: " + classId));

	    Gd_Rooms room = roomrepository.findById(roomId)
	            .orElseThrow(() -> new RuntimeException("Room not found with id: " + roomId));

	    gdClass.setGd_roooms(room);
	    return classrepository.save(gdClass);
	}
    public void deactivateRoom(Integer roomId) {
    	
    	Gd_Rooms room = roomrepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

		 room.setIs_active(false);
		    roomrepository.save(room);
		    
		    
		    Gd_Class gdClass = classrepository.findById(roomId)
		            .orElseThrow(() -> new RuntimeException("Class not found for this room"));
		    
		    gdClass.setGd_roooms(null);
		    classrepository.save(gdClass);
	}


    // Delete a room by its ID
    public void deleteRoom(int roomId) {
    	roomrepository.deleteById(roomId);
    }
    
    public Gd_Rooms updatePartialRoom(int id, Gd_Rooms partialRoom) {
        Optional<Gd_Rooms> optionalRoom = roomrepository.findById(id);
        if (optionalRoom.isEmpty()) {
            return null;
        }

        Gd_Rooms existingRoom = optionalRoom.get();

        if (partialRoom.getCapacity() != 0) {
            existingRoom.setCapacity(partialRoom.getCapacity());
        }
        
        if ( partialRoom.isIs_active() == true) {
            if (existingRoom.isIs_active() == true) {
                throw new IllegalStateException("Room is already active");
            }
            existingRoom.setIs_active(true);
        }
        // Extend this as needed for other partial updates

        return roomrepository.save(existingRoom);
    }

}
