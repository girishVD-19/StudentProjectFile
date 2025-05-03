package com.example.student.DTO;

public class StudentMarkDTO {
	
	 private String studentName;
	    private String subjectName;
	    private String className;
	    private int marks;
	    private String remark;

	    public StudentMarkDTO(String studentName, String subjectName, String className, int marks, String remark) {
	        this.studentName = studentName;
	        this.subjectName = subjectName;
	        this.className = className;
	        this.marks = marks;
	        this.remark = remark;
	    }

	    // Getters and Setters
	    public String getStudentName() {
	        return studentName;
	    }

	    public void setStudentName(String studentName) {
	        this.studentName = studentName;
	    }

	    public String getSubjectName() {
	        return subjectName;
	    }

	    public void setSubjectName(String subjectName) {
	        this.subjectName = subjectName;
	    }

	    public String getClassName() {
	        return className;
	    }

	    public void setClassName(String className) {
	        this.className = className;
	    }

	    public int getMarks() {
	        return marks;
	    }

	    public void setMarks(int marks) {
	        this.marks = marks;
	    }

	    public String getRemark() {
	        return remark;
	    }

	    public void setRemark(String remark) {
	        this.remark = remark;
	    }

}
