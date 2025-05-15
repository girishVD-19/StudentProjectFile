
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

	public void setCLASS_ID(int cLASS_ID) {
		CLASS_ID = cLASS_ID;
	}
    
	@JsonProperty("ClassName")
	public String getCLASS_NAME() {
		return CLASS_NAME;
	}

	@JsonProperty("ClassName")
	public void setCLASS_NAME(String cLASS_NAME) {
		CLASS_NAME = cLASS_NAME;
	}
    
	@JsonProperty("Std")
	public String getSTD() {
		return STD;
	}

	@JsonProperty("Std")
	public void setSTD(String sTD) {
		STD = sTD;
	}
    @JsonProperty("Room")
	public Gd_Rooms getGd_roooms() {
		return gd_rooms;
	}

    @JsonProperty("Room")
	public void setGd_roooms(Gd_Rooms gd_roooms) {
		this.gd_rooms = gd_roooms;
	}

	public List<Gd_Student> getGd_student() {
		return gd_Student;
	}

	public void setGd_student(List<Gd_Student> gd_Student) {
		this.gd_Student = gd_Student;
	}

	public List<Gd_Subject_Mapping> getGd_subject_mapping() {
		return gd_Subject_Mapping;
	}

	public void setGd_subject_mapping(List<Gd_Subject_Mapping> gd_Subject_Mapping) {
		this.gd_Subject_Mapping = gd_Subject_Mapping;
	}

	@Override
	public String toString() {
		return "GD_CLASS [CLASS_ID=" + CLASS_ID + ", CLASS_NAME=" + CLASS_NAME + ", STD=" + STD + ", gd_rooms="
				+ gd_rooms + ", gd_student=" + gd_Student + ", gd_subject_mapping=" + gd_Subject_Mapping + "]";
	}
	
	
	
	

}
