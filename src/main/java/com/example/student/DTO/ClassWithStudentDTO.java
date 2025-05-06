package com.example.student.DTO;

import java.util.List;

public class ClassWithStudentDTO {
	private Integer classId;
    private String className;
    private String std;
    private List<StudentDTOS> student;
    
    
    public ClassWithStudentDTO() {
		super();
		this.classId = classId;
		this.className = className;
		this.std = std;
		this.student = student;
	}

	public static class StudentDTOS{
    	private Integer studentId;
        private String studentName;
        private Integer rollNo;

        public StudentDTOS() {}

        public StudentDTOS(Integer studentId, String studentName, Integer rollNo) {
            this.studentId = studentId;
            this.studentName = studentName;
            this.rollNo = rollNo;
        }

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

        public Integer getRollNo() {
            return rollNo;
        }

        public void setRollNo(Integer rollNo) {
            this.rollNo = rollNo;
        }
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

	public List<StudentDTOS> getStudent() {
		return student;
	}

	public void setStudent(List<StudentDTOS> student) {
		this.student = student;
	}
    
    

}
