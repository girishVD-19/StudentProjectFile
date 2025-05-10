package com.example.student.DTO;

public class ClassDetailsDTO {

    private Integer classId;
    private String className;
    private String std;
    private RoomDTO room;

    // Constructors
    public ClassDetailsDTO() {}

    public ClassDetailsDTO(Integer classId, String className, String std, RoomDTO room) {
        this.classId = classId;
        this.className = className;
        this.std = std;
        this.room = room;
    }

    public ClassDetailsDTO(Integer classId, String className, String std) {
        this.classId = classId;
        this.className = className;
        this.std = std;
    }

    // Getters and Setters
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
}
