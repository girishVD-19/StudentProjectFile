package com.example.student.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.student.DTO.PageSortDTO;
import com.example.student.DTO.RoomDTO;
import com.example.student.DTO.RoomWithClassDTO;
import com.example.student.Service.RoomsService;
import com.example.student.entity.Gd_Class;
import com.example.student.entity.Gd_Rooms;

import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("rooms")
public class RoomsController {
	
	@Autowired
	private RoomsService roomservice;
	
	private static final Logger logger = LoggerFactory.getLogger(RoomsController.class);
//	
	@GetMapping("/")
	@Operation(summary="To Get all the data Of Rooms",
	description="To Get all the of Rooms"
	)
	public ResponseEntity<PageSortDTO<Gd_Rooms>> getAllRooms(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size,
	        @RequestParam(required = false) Boolean isActive) {

	    Pageable pageable = PageRequest.of(page, size);
	    PageSortDTO<Gd_Rooms> response = roomservice.getAllRooms(pageable, isActive);
	    return ResponseEntity.ok(response);
	}


    @GetMapping("/{roomId}")
    @Operation(summary="To Get all the data Of Rooms",
	description="To Get all the of Rooms"
	)
    public RoomWithClassDTO getRoomDetails(@PathVariable Integer roomId) {
        logger.info("Received roomId: {}", roomId);  // Log the received roomId
        return roomservice.getRoomWithClassDetails(roomId);
    }

    // Create or update a room
    @PostMapping("Add")
    @Operation(
    		summary="To Send data of Rooms",
    		description="To Send Data of Room And add TO database"
    		)   
    public ResponseEntity<Gd_Rooms> createOrUpdateRoom(@RequestBody Gd_Rooms room) {
        Gd_Rooms savedRoom = roomservice.createOrUpdateRoom(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRoom);
    }
    
    @PatchMapping("/{id}")
    @Operation(
    		summary="To Send data of Rooms",
    		description="To Send Data of Room And add TO database"
    		)
    public ResponseEntity<Gd_Rooms> updateRoomPartial(
            @PathVariable int id,
            @RequestBody Gd_Rooms partialRoom) {

        Gd_Rooms updatedRoom = roomservice.updatePartialRoom(id, partialRoom);

        if (updatedRoom == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedRoom);
    }
    
    @PatchMapping("/{roomId}/deactivate")
    @Operation(
    		summary="To Deactivate the room",
    		description="To Deactivate the room"
    		)
    public ResponseEntity<String> deactivateRoomAndUnlinkClass(@PathVariable Integer roomId) {
        try {
            roomservice.deactivateRoom(roomId);
            return ResponseEntity.ok("Room deactivated and class unlinked successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("An unexpected error occurred.");
        }
    }
    
    
    // Delete a room by ID
    @DeleteMapping("/{id}")
    @Operation(
    		summary="To Delete the room",
    		description="To Delete the room"
    		)
    public ResponseEntity<Void> deleteRoom(@PathVariable("id") int roomId) {
    	roomservice.deleteRoom(roomId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
