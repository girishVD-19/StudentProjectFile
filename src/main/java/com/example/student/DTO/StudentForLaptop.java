package com.example.student.DTO;

import java.util.List;

public class StudentForLaptop {

	private Integer studentId;      // Unique ID for the student
    private String studentName;     // Name of the student
    private String assignedDate;
    
    
	public StudentForLaptop(Integer studentId, String studentName, String assignedDate) {
		super();
		this.studentId = studentId;
		this.studentName = studentName;
		this.assignedDate = assignedDate;
	}
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getAssignedDate() {
		return assignedDate;
	}
	public void setAssignedDate(String assignedDate) {
		this.assignedDate = assignedDate;
	}
    
    
    
}
