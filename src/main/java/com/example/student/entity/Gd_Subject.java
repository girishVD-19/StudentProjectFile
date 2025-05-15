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
@Table(name="GD_SUBJECT")
public class Gd_Subject {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int SUBJECT_ID;
	public Gd_Subject() {
		
	}
	
	public Gd_Subject(int sUBJECT_ID, List<Gd_Subject_Mapping> gd_Subject_Mapping, String sUBJECT_NAME) {
		super();
		SUBJECT_ID = sUBJECT_ID;
		this.gd_subject_mapping = gd_Subject_Mapping;
		SUBJECT_NAME = sUBJECT_NAME;
	}
    @JsonIgnore
	@OneToMany(mappedBy="gd_subject" , cascade=CascadeType.ALL)
    private List<Gd_Subject_Mapping> gd_subject_mapping; 
    
    @Column(name = "SUBJECT_NAME" ,unique=true)
    @JsonProperty("SubjectName")
    private String SUBJECT_NAME;

	public List<Gd_Subject_Mapping> getGd_subject_mapping() {
		return gd_subject_mapping;
	}

	public void setGd_subject_mapping(List<Gd_Subject_Mapping> gd_Subject_Mapping) {
		this.gd_subject_mapping = gd_Subject_Mapping;
	}

	@JsonProperty("SubjectName") 
	public String getSUBJECT_NAME() {
		return SUBJECT_NAME;
	}
    
	@JsonProperty("SubjectName") 
	public void setSUBJECT_NAME(String sUBJECT_NAME) {
		SUBJECT_NAME = sUBJECT_NAME;
	}
	
	
	public int getSUBJECT_ID() {
		return SUBJECT_ID;
	}

	public void setSUBJECT_ID(int sUBJECT_ID) {
		SUBJECT_ID = sUBJECT_ID;
	}
   
	@Override
	public String toString() {
		return "GD_SUBJECT [SUBJECT_ID=" + SUBJECT_ID + ", gd_subject_mapping=" + gd_subject_mapping + ", SUBJECT_NAME="
				+ SUBJECT_NAME + "]";
	}
    
}
