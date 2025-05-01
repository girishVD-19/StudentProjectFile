package com.example.student.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.student.Service.RoomsService;
import com.example.student.entity.Gd_Rooms;

@RestController
@RequestMapping("rooms")
public class RoomsController {
	
	@Autowired
	private RoomsService roomservice;
	
    @GetMapping("All")
    public List<Gd_Rooms> getAllRooms() {
        return roomservice.getAllRooms();
    }

    // Get a room by ID
    @GetMapping("/{id}")
    public ResponseEntity<Gd_Rooms> getRoomById(@PathVariable("id") int roomId) {
        Optional<Gd_Rooms> room = roomservice.getRoomById(roomId);
        return room.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Create or update a room
    @PostMapping("Add")
    public ResponseEntity<Gd_Rooms> createOrUpdateRoom(@RequestBody Gd_Rooms room) {
        Gd_Rooms savedRoom = roomservice.createOrUpdateRoom(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRoom);
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<Gd_Rooms> updateRoomPartial(
            @PathVariable int id,
            @RequestBody Gd_Rooms partialRoom) {

        Gd_Rooms updatedRoom = roomservice.updatePartialRoom(id, partialRoom);

        if (updatedRoom == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedRoom);
    }

    // Delete a room by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable("id") int roomId) {
    	roomservice.deleteRoom(roomId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    
}
