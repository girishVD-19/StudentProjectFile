
package com.example.student.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name="GD_CLASS")
public class Gd_Class {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonProperty("classId")  
    private Integer CLASS_ID;
	
	@Column
	
	private String CLASS_NAME;
	@Column
	private String STD;
	
	

	@ManyToOne
	@JoinColumn(name="ROOM_ID", nullable = true)
	private Gd_Rooms gd_rooms;
	
    
    @ManyToMany(mappedBy = "classes") // This tells JPA that the mapping is already defined in the Gd_Teacher entity
    private List<Gd_Teacher> teachers;

	@OneToMany(mappedBy="gd_class", cascade = CascadeType.ALL,orphanRemoval=true)
	private List<Gd_Student> gd_Student;
	
	@OneToMany(mappedBy="gd_class", cascade = CascadeType.ALL, orphanRemoval=true)
	private List<Gd_Subject_Mapping> gd_Subject_Mapping;
	
	public Gd_Class() {
		
	}
	
	public Gd_Class(String cLASS_NAME, String sTD, Gd_Rooms gd_roooms, List<Gd_Student> gd_Student,
			List<Gd_Subject_Mapping> gd_Subject_Mapping) {
		super();
		CLASS_NAME = cLASS_NAME;
		STD = sTD;
		this.gd_rooms = gd_roooms;
		this.gd_Student = gd_Student;
		this.gd_Subject_Mapping = gd_Subject_Mapping;
	}

	public Integer getCLASS_ID() {
		return CLASS_ID;
	}

	public void setCLASS_ID(Integer cLASS_ID) {
		CLASS_ID = cLASS_ID;
	}

	public String getCLASS_NAME() {
		return CLASS_NAME;
	}

	public void setCLASS_NAME(String cLASS_NAME) {
		CLASS_NAME = cLASS_NAME;
	}

	public String getSTD() {
		return STD;
	}

	public void setSTD(String sTD) {
		STD = sTD;
	}

	public Gd_Rooms getGd_rooms() {
		return gd_rooms;
	}

	public void setGd_rooms(Gd_Rooms gd_rooms) {
		this.gd_rooms = gd_rooms;
	}

	public List<Gd_Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<Gd_Teacher> teachers) {
		this.teachers = teachers;
	}

	public List<Gd_Student> getGd_Student() {
		return gd_Student;
	}

	public void setGd_Student(List<Gd_Student> gd_Student) {
		this.gd_Student = gd_Student;
	}

	public List<Gd_Subject_Mapping> getGd_Subject_Mapping() {
		return gd_Subject_Mapping;
	}

	public void setGd_Subject_Mapping(List<Gd_Subject_Mapping> gd_Subject_Mapping) {
		this.gd_Subject_Mapping = gd_Subject_Mapping;
	}

	
	
	
	

}
