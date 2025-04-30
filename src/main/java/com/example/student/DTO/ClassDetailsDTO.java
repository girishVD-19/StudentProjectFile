package com.example.student.DTO;

public class ClassDetailsDTO {
	
	private Integer classId;
    private String className;
    private String std;  // Added STD (standard) here
   // Room details in ClassDetailsDTO

    // Constructor
    public ClassDetailsDTO(Integer classId, String className, String std) {
        this.classId = classId;
        this.className = className;
        this.std = std;
    
    }

    public ClassDetailsDTO(Integer class_ID, String class_NAME) {
		// TODO Auto-generated constructor stub
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
}
