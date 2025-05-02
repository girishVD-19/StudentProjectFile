package com.example.student.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="GD_LAPTOP_HISTORY")
public class Gd_Laptop_History {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private int HISTORY_ID;
	
	@ManyToOne
	@JoinColumn(name="LAPTOP_ID")
	private Gd_Laptop gd_laptop;
	
	public Gd_Laptop_History() {
		
	}
	
	public Gd_Laptop_History(Gd_Laptop gd_laptop, Gd_Student gd_Student, LocalDate aSSIGNED_DATE,
			LocalDate return_Date) {
		super();
		this.gd_laptop = gd_laptop;
		this.gd_student = gd_Student;
		ASSIGNED_DATE = aSSIGNED_DATE;
		Return_Date = return_Date;
	}

	public LocalDate getReturn_Date() {
		return Return_Date;
	}

	public void setReturn_Date(LocalDate return_Date) {
		Return_Date = return_Date;
	}

	@ManyToOne
	@JoinColumn(name="STUDENT_ID")
	private Gd_Student gd_student;
	
	private LocalDate ASSIGNED_DATE;
	
	@Column(name="RETURN_DATE")
	private LocalDate Return_Date;
	

	public int getHISTORY_ID() {
		return HISTORY_ID;
	}

	public void setHISTORY_ID(int hISTORY_ID) {
		HISTORY_ID = hISTORY_ID;
	}

	public Gd_Laptop getGd_laptop() {
		return gd_laptop;
	}

	public void setGd_laptop(Gd_Laptop gd_laptop) {
		this.gd_laptop = gd_laptop;
	}

	public Gd_Student getGd_student() {
		return gd_student;
	}

	public void setGd_student(Gd_Student gd_Student) {
		this.gd_student = gd_Student;
	}

	public LocalDate getASSIGNED_DATE() {
		return ASSIGNED_DATE;
	}

	public void setASSIGNED_DATE(LocalDate aSSIGNED_DATE) {
		ASSIGNED_DATE = aSSIGNED_DATE;
	}

	@Override
	public String toString() {
		return "Gd_Laptop_History [HISTORY_ID=" + HISTORY_ID + ", gd_laptop=" + gd_laptop + ", gd_student=" + gd_student
				+ ", ASSIGNED_DATE=" + ASSIGNED_DATE + ", Return_Date=" + Return_Date + "]";
	}
	
	
	
	
	
	
}
