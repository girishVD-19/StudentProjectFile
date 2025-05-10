package com.example.student.DTO;

import java.util.List;

public class LaptopbyIdDTO {
	
	private int laptopId;
    private int modelNo;
    private int isAssigned;
    private StudentDTO student; // Student data as an object
    private List<HistoryDTO> history; // History records

    // Constructor
    public LaptopbyIdDTO (int laptopId, int modelNo, int isAssigned, StudentDTO student, List<HistoryDTO> history) {
        this.laptopId = laptopId;
        this.modelNo = modelNo;
        this.isAssigned = isAssigned;
        this.student = student;
        this.history = history;
    }

    // Getters and Setters
    public int getLaptopId() {
        return laptopId;
    }

    public void setLaptopId(int laptopId) {
        this.laptopId = laptopId;
    }

    public int getModelNo() {
        return modelNo;
    }

    public void setModelNo(int modelNo) {
        this.modelNo = modelNo;
    }

    public int getIsAssigned() {
        return isAssigned;
    }

    public void setIsAssigned(int isAssigned) {
        this.isAssigned = isAssigned;
    }

    public StudentDTO getStudent() {
        return student;
    }

    public void setStudent(StudentDTO student) {
        this.student = student;
    }

    public List<HistoryDTO> getHistory() {
        return history;
    }

    public void setHistory(List<HistoryDTO> history) {
        this.history = history;
    }

    // Inner DTO for Student
    public static class StudentDTO {
        private int studentId;
        private String studentName;
        private String assignedDate;

        // Constructor
        public StudentDTO(int studentId, String studentName, String assignedDate) {
            this.studentId = studentId;
            this.studentName = studentName;
            this.assignedDate = assignedDate;
        }

        // Getters and Setters
        public int getStudentId() {
            return studentId;
        }

        public void setStudentId(int studentId) {
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

    // Inner DTO for History
    public static class HistoryDTO {
        private int historyId;
        private int studentId;
        private String assignedDate;
        private String returnDate;

        // Constructor
        public HistoryDTO(int historyId, int studentId, String assignedDate, String returnDate) {
            this.historyId = historyId;
            this.studentId = studentId;
            this.assignedDate = assignedDate;
            this.returnDate = returnDate;
        }

        // Getters and Setters
        public int getHistoryId() {
            return historyId;
        }

        public void setHistoryId(int historyId) {
            this.historyId = historyId;
        }

        public int getStudentId() {
            return studentId;
        }

        public void setStudentId(int studentId) {
            this.studentId = studentId;
        }

        public String getAssignedDate() {
            return assignedDate;
        }

        public void setAssignedDate(String assignedDate) {
            this.assignedDate = assignedDate;
        }

        public String getReturnDate() {
            return returnDate;
        }

        public void setReturnDate(String returnDate) {
            this.returnDate = returnDate;
        }
    }
}
