package com.example.student.DTO;

import java.util.List;

public class SubjectWithClassDTO {
	
	 private int subjectId;
	    private String subjectName;
	    private List<ClassDTO> classes;  
	   
	    public SubjectWithClassDTO() {
	    }
	    public SubjectWithClassDTO(int subjectId, String subjectName, List<ClassDTO> classes) {
			super();
			this.subjectId = subjectId;
			this.subjectName = subjectName;
			this.classes = classes;
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


		public List<ClassDTO> getClasses() {
			return classes;
		}


		public void setClasses(List<ClassDTO> classes) {
			this.classes = classes;
		}


		public static class ClassDTO{
	    	Integer classId;
	    	String ClassName;
	    	String Std;
			
			public ClassDTO(Integer classId, String className, String std) {
				super();
				this.classId = classId;
				ClassName = className;
				Std = std;
			}
			public Integer getClassId() {
				return classId;
			}
			public void setClassId(Integer classId) {
				this.classId = classId;
			}
			public String getClassName() {
				return ClassName;
			}
			public void setClassName(String className) {
				ClassName = className;
			}
			public String getStd() {
				return Std;
			}
			public void setStd(String std) {
				Std = std;
			}
	    	
	    }

}
