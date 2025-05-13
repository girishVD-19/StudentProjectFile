package com.example.student.DTO;

import java.util.List;

public class MarkResponseDTO {
	
	private studentDTO student;
	private classDTO classDetails;
	private List<SubjectMarkDTOs> markDetails;
	
	
	public MarkResponseDTO(studentDTO student, classDTO classdetails, List<SubjectMarkDTOs> markDetails) {
		super();
		this.student = student;
		this.classDetails = classdetails;
		this.markDetails = markDetails;
	}
	
	public MarkResponseDTO() {
		// TODO Auto-generated constructor stub
	}

	


	


	public studentDTO getStudent() {
		return student;
	}

	public void setStudent(studentDTO student) {
		this.student = student;
	}

	
	public void setClassDetails(classDTO classdetails) {
		this.classDetails = classdetails;
	}
	public classDTO getClassDetails() {
		return classDetails;
	}

	public void setMarkDetails(List<SubjectMarkDTOs> markDetails) {
		this.markDetails = markDetails;
	}

	public List<SubjectMarkDTOs> getMarkDetails() {
		return markDetails;
	}


	public static class studentDTO{
		private Integer studentId;
		private String StudentName;
		
		
		public studentDTO(Integer studentId, String studentName) {
			super();
			this.studentId = studentId;
			StudentName = studentName;
		}
		public Integer getStudentId() {
			return studentId;
		}
		public void setStudentId(Integer studentId) {
			this.studentId = studentId;
		}
		public String getStudentName() {
			return StudentName;
		}
		public void setStudentName(String studentName) {
			StudentName = studentName;
		}
		
	}
	
	public static class classDTO{
		private Integer classId;
		private String className;
		private String std;
		
		
		public classDTO(Integer classId, String className, String std) {
			super();
			this.classId = classId;
			this.className = className;
			this.std = std;
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
	}
		
		public static class SubjectMarkDTOs{
			private Integer markId;
			private String SubjectName;
			private Integer marks;
			private String remark;
			public SubjectMarkDTOs(Integer markId, String subjectName, Integer marks, String remark) {
				super();
				this.markId = markId;
				SubjectName = subjectName;
				this.marks = marks;
				this.remark = remark;
			}
			public Integer getMarkId() {
				return markId;
			}
			public void setMarkId(Integer markId) {
				this.markId = markId;
			}
			public String getSubjectName() {
				return SubjectName;
			}
			public void setSubjectName(String subjectName) {
				SubjectName = subjectName;
			}
			public Integer getMarks() {
				return marks;
			}
			public void setMarks(Integer marks) {
				this.marks = marks;
			}
			public String getRemark() {
				return remark;
			}
			public void setRemark(String remark) {
				this.remark = remark;
			}
			
			
		}
		
		
		
	
	

}
