package com.example.student.Service;


import java.util.List;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.student.DTO.PageSortDTO;
import com.example.student.DTO.RoomWithClassDTO;
import com.example.student.DTO.RoomWithClassDTO.ClassDTO;
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
	
	 @Autowired
	    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	 
	 private static final Logger logger = LoggerFactory.getLogger(RoomsService.class);
	
	public PageSortDTO<Gd_Rooms> getAllRooms(Pageable pageable, Boolean isActive) {
	    Page<Gd_Rooms> roomsPage = 
	        isActive != null 
	        ? roomrepository.findByIs_active(isActive, pageable)
	        : roomrepository.findAll(pageable);
	    System.out.println("isActive: " + isActive);
	    PageSortDTO.PaginationDetails paginationDetails = new PageSortDTO.PaginationDetails(
	        roomsPage.getNumber() + 1,
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

    public RoomWithClassDTO getRoomWithClassDetails(Integer roomId) {
        String sql = "SELECT r.ROOM_ID, r.CAPACITY, r.IS_ACTIVE, c.CLASS_ID, c.CLASS_NAME, c.STD " +
                     "FROM GD_ROOMS r " +
                     "LEFT JOIN GD_CLASS c ON r.ROOM_ID = c.ROOM_ID " +
                     "WHERE r.ROOM_ID = :roomId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("roomId", roomId);

        // Log the query and parameters
        logger.info("Executing query: {} with roomId: {}", sql, roomId);

        List<RoomWithClassDTO> result = namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> {
            RoomWithClassDTO dto = new RoomWithClassDTO();
            dto.setRoom_id(rs.getInt("ROOM_ID"));
            dto.setCapacity(rs.getInt("CAPACITY"));
            dto.setIsActive(rs.getBoolean("IS_ACTIVE"));

            RoomWithClassDTO.ClassDTO classDto;

            // If class details are available, create ClassDTO; otherwise, create an empty ClassDTO
            if (rs.getObject("CLASS_ID") != null && rs.getInt("CLASS_ID") > 0) {
                classDto = new RoomWithClassDTO.ClassDTO(
                        rs.getInt("CLASS_ID"),
                        rs.getString("CLASS_NAME"),
                        rs.getString("STD")
                );
            } else {
                // Create an empty ClassDTO if no class is associated
                classDto = new RoomWithClassDTO.ClassDTO();
            }

            // Set the classes field to the appropriate ClassDTO
            dto.setClasses(classDto);

            return dto;
        });

        // Log the query result
        logger.info("Query result: {}", result);

        // Return the first result or null if no result found
        return result.isEmpty() ? null : result.get(0);
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
