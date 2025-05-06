package com.example.student.entity;

import java.util.List;

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
@Table(name="GD_LAPTOP")
public class Gd_Laptop {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonProperty("laptopId")
    private Integer LAPTOP_ID;
	
	@Column(unique=true)
	@JsonProperty("modelNo")
	private int MODEL_NO;


	@Column(name="IS_ASSIGNED")
	private int IS_ASSIGNED;
	
	@Column(name="IS_ALIVE")
	private boolean IS_ALIVE;

	
public boolean isIS_ALIVE() {
		return IS_ALIVE;
	}

	public void setIS_ALIVE(boolean iS_ALIVE) {
		IS_ALIVE = iS_ALIVE;
	}

public int getMODEL_NO() {
		return MODEL_NO;
	}

	public void setMODEL_NO(int mODEL_NO) {
		MODEL_NO = mODEL_NO;
	}

	public void setLAPTOP_ID(Integer lAPTOP_ID) {
		LAPTOP_ID = lAPTOP_ID;
	}

@OneToMany(mappedBy="gd_laptop" , cascade=CascadeType.ALL)
private  List<Gd_Student> gd_student;

@OneToMany(mappedBy="gd_laptop" , cascade=CascadeType.ALL )
private List<Gd_Laptop_History> gd_Laptop_History;



public List<Gd_Student> getGd_student() {
	return gd_student;
}

public Integer getLAPTOP_ID() {
	return LAPTOP_ID;
}

public Gd_Laptop() {
	
}




public Gd_Laptop(int mODEL_NO, int iS_ASSIGNED, boolean iS_ALIVE, List<Gd_Student> gd_student,
		List<Gd_Laptop_History> gd_Laptop_History) {
	super();
	MODEL_NO = mODEL_NO;
	IS_ASSIGNED = iS_ASSIGNED;
	IS_ALIVE = iS_ALIVE;
	this.gd_student = gd_student;
	this.gd_Laptop_History = gd_Laptop_History;
}

public void setLAPTOP_ID(int lAPTOP_ID) {
	LAPTOP_ID = lAPTOP_ID;
}


public int getIS_ASSIGNED() {
	return IS_ASSIGNED;
}

public void setIS_ASSIGNED(int iS_ASSIGNED) {
	IS_ASSIGNED = iS_ASSIGNED;
}



public void setGd_student(List<Gd_Student> gd_Student) {
	this.gd_student = gd_Student;
}

public List<Gd_Laptop_History> getGd_laptop_history() {
	return gd_Laptop_History;
}

public void setGd_laptop_history(List<Gd_Laptop_History> gd_Laptop_History) {
	this.gd_Laptop_History = gd_Laptop_History;
}

public List<Gd_Laptop_History> getGd_Laptop_History() {
	return gd_Laptop_History;
}

public void setGd_Laptop_History(List<Gd_Laptop_History> gd_Laptop_History) {
	this.gd_Laptop_History = gd_Laptop_History;
}
@Override
public String toString() {
	return "Gd_Laptop [LAPTOP_ID=" + LAPTOP_ID + ", MODEL_NO=" + MODEL_NO + ", IS_ASSIGNED=" + IS_ASSIGNED
			+ ", gd_student=" + gd_student + ", gd_Laptop_History=" + gd_Laptop_History + "]";
}

}
