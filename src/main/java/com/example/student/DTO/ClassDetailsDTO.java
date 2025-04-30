package com.example.student.DTO;

public class ClassDetailsDTO {
	
	private Integer classId;
    private String className;
    private String std;
    private Integer roomId;
    private Integer roomCapacity;

    public ClassDetailsDTO(Integer classId, String className, String std, Integer roomId, Integer roomCapacity) {
        this.classId = classId;
        this.className = className;
        this.std = std;
        this.roomId = roomId;
        this.roomCapacity = roomCapacity;
    }

    public ClassDetailsDTO(Integer class_ID, String class_NAME, String std) {
		// TODO Auto-generated constructor stub
    	this.classId=class_ID;
    	this.className=class_NAME;
    	this.std=std;
	}

	// Getters and Setters
    public Integer getClassId() { return classId; }
    public void setClassId(Integer classId) { this.classId = classId; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getStd() { return std; }
    public void setStd(String std) { this.std = std; }

    public Integer getRoomId() { return roomId; }
    public void setRoomId(Integer roomId) { this.roomId = roomId; }

    public Integer getRoomCapacity() { return roomCapacity; }
    public void setRoomCapacity(Integer roomCapacity) { this.roomCapacity = roomCapacity; }
}