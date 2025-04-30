package com.example.student.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="GD_SUBJECT_MAPPTING")
public class Gd_Subject_Mapping {
	
	@Id
	private int SR_NO;
	
	
  @ManyToOne
  @JoinColumn(name="CLASS_ID")
  @JsonIgnore
  private Gd_Class gd_class;
  
  @ManyToOne
  @JoinColumn(name="SUBJECT_ID")
  @JsonIgnore
  private Gd_Subject gd_subject;
  
  public int getSR_NO() {
	return SR_NO;
}

public void setSR_NO(int sR_NO) {
	SR_NO = sR_NO;
}

public Gd_Subject_Mapping() {
	  
  }


public Gd_Subject_Mapping(int sR_NO, Gd_Class gd_class, Gd_Subject gd_subject) {
	super();
	SR_NO = sR_NO;
	this.gd_class = gd_class;
	this.gd_subject = gd_subject;
}

public Gd_Class getGd_class() {
	return gd_class;
}

public void setGd_class(Gd_Class gd_Class) {
	this.gd_class = gd_Class;
}

public Gd_Subject getGd_subject() {
	return gd_subject;
}

public void setGd_subject(Gd_Subject gd_Subject) {
	this.gd_subject = gd_Subject;
}

@Override
public String toString() {
	return "GD_SUBJECT_MAPPING [gd_class=" + gd_class + ", gd_subject=" + gd_subject + "]";
}
  
  
  
}
