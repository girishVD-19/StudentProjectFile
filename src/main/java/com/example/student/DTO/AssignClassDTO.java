package com.example.student.DTO;

import java.util.List;

public class AssignClassDTO {
 private int TeacherId;
 private List<Integer> classId;
 
 
public AssignClassDTO(int teacherId, List<Integer> classId) {
	super();
	TeacherId = teacherId;
	this.classId = classId;
}
public int getTeacherId() {
	return TeacherId;
}
public void setTeacherId(int teacherId) {
	TeacherId = teacherId;
}
public List<Integer> getClassId() {
	return classId;
}
public void setClassId(List<Integer> classId) {
	this.classId = classId;
}
 
 
}
