package com.example.student.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="GD_STUDENT")
public class Gd_Student {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private int STUDENT_ID;
	
	public Gd_Student() {
		
	}
	
	public Gd_Student(int sTUDENT_ID, Integer rOLL_NO, String nAME, boolean isActive, Gd_Class gd_class,
			String cITY, Gd_Laptop gd_laptop, List<Gd_Student_Mark> gd_student_mark,
			List<Gd_Laptop_History> gd_laptop_history) {
		super();
		STUDENT_ID = sTUDENT_ID;
		ROLL_NO = rOLL_NO;
		NAME = nAME;
		this.isActive = isActive;
		this.gd_class = gd_class;
		
		CITY = cITY;
		this.gd_laptop = gd_laptop;
		this.gd_student_mark = gd_student_mark;
		this.gd_laptop_history = gd_laptop_history;
	}

	@Column(name="ROLL_NO")
	@JsonProperty("rollno")
    private Integer ROLL_NO;
	
	@Column
	private String NAME;
	
	@Column(name = "IS_ACTIVE")
    private boolean isActive;  
	
	

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}


	@ManyToOne
	@JoinColumn(name="CLASS_ID")
	private Gd_Class gd_class;
	

	@Column
	private String CITY;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LAPTOP_ID", nullable = true)
	private Gd_Laptop gd_laptop;

	
	@OneToMany(mappedBy="gd_student", cascade=CascadeType.ALL)
	private List<Gd_Student_Mark> gd_student_mark;

	@OneToMany(mappedBy="gd_student", cascade=CascadeType.ALL)
	private List<Gd_Laptop_History> gd_laptop_history;

	public int getSTUDENT_ID() {
		return STUDENT_ID;
	}

	public void setSTUDENT_ID(int sTUDENT_ID) {
		STUDENT_ID = sTUDENT_ID;
	}

	public Integer getROLL_NO() {
		return ROLL_NO;
	}

	public void setROLL_NO(int rOLL_NO) {
		ROLL_NO = rOLL_NO;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public Gd_Class getGd_class() {
		return gd_class;
	}

	public void setGd_class(Gd_Class gd_Class) {
		this.gd_class = gd_Class;
	}

	

	public String getCITY() {
		return CITY;
	}

	public void setCITY(String cITY) {
		CITY = cITY;
	}

	public Gd_Laptop getGd_laptop() {
		return gd_laptop;
	}

	public void setGd_laptop(Gd_Laptop gd_Laptop) {
		this.gd_laptop = gd_Laptop;
	}

	public List<Gd_Student_Mark> getGd_student_mark() {
		return gd_student_mark;
	}

	public void setGd_student_mark(List<Gd_Student_Mark> gd_Student_Mark) {
		this.gd_student_mark = gd_Student_Mark;
	}

	public List<Gd_Laptop_History> getGd_laptop_history() {
		return gd_laptop_history;
	}

	public void setGd_laptop_history(List<Gd_Laptop_History> gd_Laptop_History) {
		this.gd_laptop_history = gd_Laptop_History;
	}

	@Override
	public String toString() {
		return "Gd_Student [STUDENT_ID=" + STUDENT_ID + ", ROLL_NO=" + ROLL_NO + ", NAME=" + NAME + ", isActive="
				+ isActive + ", gd_class=" + gd_class + ", CITY=" + CITY + ", gd_laptop=" + gd_laptop
				+ ", gd_student_mark=" + gd_student_mark + ", gd_laptop_history=" + gd_laptop_history + "]";
	}
	
	
	

}
