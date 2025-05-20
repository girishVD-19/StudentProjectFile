package com.example.student.DTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClassSummary {
	
	private Integer classId;
    private String className;
    private String std;
    private RoomDTO room;
    private Set<SubjectDTO> subjects = new HashSet<>();
    private Set<StudentForClass> students = new HashSet<>();
	public ClassSummary(Integer classId, String className, String std, RoomDTO room, Set<SubjectDTO> subjects,
			Set<StudentForClass> students) {
		super();
		this.classId = classId;
		this.className = className;
		this.std = std;
		this.room = room;
		this.subjects = subjects != null ? subjects : new HashSet<>();
        this.students = students != null ? students : new HashSet<>();
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
	public Set<SubjectDTO> getSubjects() {
		return subjects;
	}
	public void setSubjects(Set<SubjectDTO> subjects) {
		this.subjects = subjects;
	}
	public Set<StudentForClass> getStudents() {
		return students;
	}
	public void setStudents(Set<StudentForClass> students) {
		this.students = students;
	}
}
