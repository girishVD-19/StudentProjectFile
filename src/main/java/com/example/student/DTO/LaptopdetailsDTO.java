package com.example.student.DTO;

import java.util.List;

public class LaptopdetailsDTO {
    private Integer laptopId;
    private Integer modelNo;
	private Integer isAssigned;
    private Integer isActive;
    private Object students;

    // Getters and Setters
    public Integer getLaptopId() {
        return laptopId;
    }

    public void setLaptopId(Integer laptopId) {
        this.laptopId = laptopId;
    }

    public Integer getModelNo() {
        return modelNo;
    }

    public void setModelNo(Integer modelNo) {
        this.modelNo = modelNo;
    }

    public Integer getIsAssigned() {
        return isAssigned;
    }

    public void setIsAssigned(Integer i) {
        this.isAssigned = i;
    }

    public Object getStudents() {
        return students;
    }

    public void setStudents(Object students) {
        this.students = students;
    }
    
    public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}


    // Inner class for StudentDTO
    public static class StudentDTO {
        private Integer studentId;
        private String studentName;
        private String assignedDate;

        // Getters and Setters
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
}
