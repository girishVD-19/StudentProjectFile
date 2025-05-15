package com.example.student.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="GD_SUBJECT_MAPPTING")
public class Gd_Subject_Mapping {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int SR_NO;
	
	
	@ManyToOne
    @JoinColumn(name = "CLASS_ID", referencedColumnName = "CLASS_ID")
	@JsonIgnore
    private Gd_Class gd_class;

    @ManyToOne
    @JoinColumn(name = "SUBJECT_ID", referencedColumnName = "SUBJECT_ID")
    @JsonIgnore
    private Gd_Subject gd_subject;
    
  @OneToMany(mappedBy="gd_subject_mapping", cascade= CascadeType.ALL)
  private List<Gd_Student_Mark> gd_student_mark;
  
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

public Gd_Subject_Mapping(Gd_Class gdClass, Gd_Subject gdSubject) {
	// TODO Auto-generated constructor stub
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
