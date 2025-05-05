package com.example.student.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.student.DTO.RoomDTO;
import com.example.student.entity.Gd_Rooms;
import com.example.student.repository.RoomRepository;

@Service
public class RoomsService {
	
	@Autowired
	private RoomRepository roomrepository;
	public List<Gd_Rooms> getAllRooms() {
        return roomrepository.findAll();
    }

    // Get a room by its ID
    public Optional<Gd_Rooms> getRoomById(int roomId) {
        return roomrepository.findById(roomId);
    }

    // Create or update a room
    public Gd_Rooms createOrUpdateRoom(Gd_Rooms room) {
        return roomrepository.save(room);
    }

    public List<RoomDTO> getRoomWithClasses(Integer roomId) {
        List<Object[]> results = roomrepository.findRoomWithClasses(roomId);
        Map<Integer, RoomDTO> roomMap = new HashMap<>();
        for (Object[] result : results) {
            Integer roomId1 = (Integer) result[0];
            Integer capacity = (Integer) result[1];
            Integer classId = (Integer) result[2];
            String className = (String) result[3];

            RoomDTO roomDTO = roomMap.computeIfAbsent(roomId1, id -> new RoomDTO(id, capacity, new ArrayList<>()));
            roomDTO.getClasses().add(new RoomDTO.ClassDTO(classId, className));
        }
        return new ArrayList<>(roomMap.values());
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

        // Extend this as needed for other partial updates

        return roomrepository.save(existingRoom);
    }

}
