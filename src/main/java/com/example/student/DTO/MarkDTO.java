package com.example.student.DTO;

public class MarkDTO {
	
	private int mark_ID;
    private int student_ID;
    private String Name;
    private int subject_ID;
    private String SubjectName;
    private int marks;
    private String remark;
    
    
		public MarkDTO(int mark_ID, int student_ID, String name, int subject_ID, String subjectName, int marks,
			String remark) {
		super();
		this.mark_ID = mark_ID;
		this.student_ID = student_ID;
		Name = name;
		this.subject_ID = subject_ID;
		SubjectName = subjectName;
		this.marks = marks;
		this.remark = remark;
	}
	public int getMark_ID() {
		return mark_ID;
	}
	public void setMark_ID(int mark_ID) {
		this.mark_ID = mark_ID;
	}
	public int getStudent_ID() {
		return student_ID;
	}
	public void setStudent_ID(int student_ID) {
		this.student_ID = student_ID;
	}
	public int getSubject_ID() {
		return subject_ID;
	}
	public void setSubject_ID(int subject_ID) {
		this.subject_ID = subject_ID;
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
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getSubjectName() {
		return SubjectName;
	}
	public void setSubjectName(String subjectName) {
		SubjectName = subjectName;
	}
	
	
    
    

}
