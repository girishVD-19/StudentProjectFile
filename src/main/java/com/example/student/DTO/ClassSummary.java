package com.example.student.DTO;

import java.util.ArrayList;
import java.util.List;

public class ClassSummary {
	
	private Integer classId;
    private String className;
    private String std;
    private RoomDTO room;
    private List<SubjectDTO> subjects = new ArrayList<>();
    private List<StudentForClass> students = new ArrayList<>();
	public ClassSummary(Integer classId, String className, String std, RoomDTO room, List<SubjectDTO> subjects,
			List<StudentForClass> students) {
		super();
		this.classId = classId;
		this.className = className;
		this.std = std;
		this.room = room;
		this.subjects = subjects != null ? subjects : new ArrayList<>();
        this.students = students != null ? students : new ArrayList<>();
	}
	public Integer getClassId() {
		return classId;
	}
	public void setClassId(Integer classId) {
		this.classId = classId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getStd() {
		return std;
	}
	public void setStd(String std) {
		this.std = std;
	}
	public RoomDTO getRoom() {
		return room;
	}
	public void setRoom(RoomDTO room) {
		this.room = room;
	}
	public List<SubjectDTO> getSubjects() {
		return subjects;
	}
	public void setSubjects(List<SubjectDTO> subjects) {
		this.subjects = subjects;
	}
	public List<StudentForClass> getStudents() {
		return students;
	}
	public void setStudents(List<StudentForClass> students) {
		this.students = students;
	}
}
