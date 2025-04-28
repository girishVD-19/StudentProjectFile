package com.example.student.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private int Room_Id;
	
	@Column(name="CAPACITY")
	private int Capacity;
	
	@JsonIgnore
	@OneToMany(mappedBy="gd_rooms" , cascade=CascadeType.ALL)
	private List<Gd_Class> gd_class;
	
	public Gd_Rooms() {
		
	}

	public Gd_Rooms(int cAPACITY, List<Gd_Class> gd_Class) {
		super();
		Capacity = cAPACITY;
		this.gd_class = gd_Class;
	}

	public int getRoom_Id() {
		return Room_Id;
	}

	public void setRoom_Id(int rOOM_ID) {
		Room_Id = rOOM_ID;
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
		return "GD_ROOMS [ROOM_ID=" + Room_Id + ", CAPACITY=" + Capacity + ", gd_class=" + gd_class + "]";
	}
	
	
	

}
