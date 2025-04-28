package com.example.student.DTO;

public class RoomDTO {
	
	private int room_id;
	private int capacity;
	
	public RoomDTO() {
		
	}
	public RoomDTO(int room_id, int capacity) {
		super();
		this.room_id = room_id;
		this.capacity = capacity;
	}
	public int getRoom_id() {
		return room_id;
	}
	public void setRoom_id(int room_id) {
		this.room_id = room_id;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

}
