package com.example.student.DTO;

public class StudentForClass {
	
	private Integer studentId;
    private String name;
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public StudentForClass(Integer studentId, String name) {
		super();
		this.studentId = studentId;
		this.name = name;
	}
 

}
