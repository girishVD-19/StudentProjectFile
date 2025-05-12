package com.example.student.DTO;

import java.util.List;

public class RoomDTO {
	
	private Integer room_id;
	private Integer capacity;
		
	public RoomDTO() {
		
	}
	
	public Integer getRoom_id() {
		return room_id;
	}
//	public RoomDTO(Integer room_id, Integer capacity, Integer classId, String className) {
//        this.room_id = room_id;
//        this.capacity = capacity;
//        // Add the class details as a ClassDTO object
//        this.classes = List.of(new ClassDTO(classId, className));
//    }
	
	 public RoomDTO(Integer room_id, Integer capacity) {
	        this.room_id = room_id;
	        this.capacity = capacity;
	       
	    }
	public void setRoom_id(Integer room_id) {
		this.room_id = room_id;
	}
	public Integer getCapacity() {
		return capacity;
	}
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

}

