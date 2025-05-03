package com.example.student.DTO;

public class MarkDTO {
	
	
	    private int markId;
	    private int studentId;
	    private String studentName;
	    private int subjectId;
	    private String subjectName;
	    private int marks;
	    private String remark;

	    public MarkDTO(int markId, int studentId, String studentName,
	                   int subjectId, String subjectName, int marks, String remark) {
	        this.markId = markId;
	        this.studentId = studentId;
	        this.studentName = studentName;
	        this.subjectId = subjectId;
	        this.subjectName = subjectName;
	        this.marks = marks;
	        this.remark = remark;
	    }

		public int getMarkId() {
			return markId;
		}

		public void setMarkId(int markId) {
			this.markId = markId;
		}

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

		public int getSubjectId() {
			return subjectId;
		}

		public void setSubjectId(int subjectId) {
			this.subjectId = subjectId;
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
