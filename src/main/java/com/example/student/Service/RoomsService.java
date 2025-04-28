package com.example.student.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.student.entity.Gd_Rooms;
import com.example.student.repository.RoomReopsitory;

@Service
public class RoomsService {
	
	@Autowired
	private RoomReopsitory roomrepository;
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

    // Delete a room by its ID
    public void deleteRoom(int roomId) {
    	roomrepository.deleteById(roomId);
    }

}
