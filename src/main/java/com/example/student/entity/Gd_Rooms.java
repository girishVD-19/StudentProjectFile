package com.example.student.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="GD_ROOMS")
public class Gd_Rooms {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private int ROOM_ID;
	
	@Column(name="CAPACITY")
	private int Capacity;
	
	@Column(name="IS_ACTIVE")
	private boolean is_active;
	
	public Gd_Rooms(int capacity, boolean is_active, List<Gd_Class> gd_class) {
		super();
		Capacity = capacity;
		this.is_active = is_active;
		this.gd_class = gd_class;
	}

	public boolean isIs_active() {
		return is_active;
	}

	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
	}
	
   @JsonIgnore
	@OneToMany(mappedBy="gd_rooms")
	private List<Gd_Class> gd_class;
	
	public Gd_Rooms() {
		
	}

	public Gd_Rooms(int cAPACITY, List<Gd_Class> gd_Class) {
		super();
		Capacity = cAPACITY;
		this.gd_class = gd_Class;
	}

	@JsonProperty("RoomId")
	public int getRoom_Id() {
		return ROOM_ID;
	}

	@JsonProperty("RoomId")
	public void setRoom_Id(int rOOM_ID) {
		ROOM_ID = rOOM_ID;
	}

	public int getCapacity() {
		return Capacity;
	}
	public void setCapacity(int cAPACITY) {
		Capacity = cAPACITY;
	}

	public List<Gd_Class> getGd_class() {
		return gd_class;
	}

	public void setGd_class(List<Gd_Class> gd_Class) {
		this.gd_class = gd_Class;
	}

	@Override
	public String toString() {
		return "GD_ROOMS [ROOM_ID=" + ROOM_ID + ", CAPACITY=" + Capacity + ", gd_class=" + gd_class + "]";
	}
	
	
	

}
