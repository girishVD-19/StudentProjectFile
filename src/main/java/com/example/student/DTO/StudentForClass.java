package com.example.student.DTO;

import java.util.Objects;

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
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentForClass)) return false;
        StudentForClass that = (StudentForClass) o;
        return Objects.equals(studentId, that.studentId) &&
               Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, name);
    }
 

}
