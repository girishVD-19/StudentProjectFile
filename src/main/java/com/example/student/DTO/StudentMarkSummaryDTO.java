package com.example.student.DTO;

public class StudentMarkSummaryDTO {
	
	private String subjectName;
    private int marks;
    private String remark;

    public StudentMarkSummaryDTO(String subjectName, int marks, String remark) {
        this.subjectName = subjectName;
        this.marks = marks;
        this.remark = remark;
    }

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
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
