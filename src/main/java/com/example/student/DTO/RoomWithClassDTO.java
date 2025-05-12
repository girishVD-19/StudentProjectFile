package com.example.student.DTO;

public class RoomWithClassDTO {
	
	private Integer room_id;
	private Integer capacity;
	private ClassDTO classes;
	
	
	public Integer getRoom_id() {
		return room_id;
	}


	public void setRoom_id(Integer room_id) {
		this.room_id = room_id;
	}


	public Integer getCapacity() {
		return capacity;
	}


	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}


	public ClassDTO getClasses() {
		return classes;
	}


	public void setClasses(ClassDTO classes) {
		this.classes = classes;
	}

	public static class ClassDTO{
		
		private Integer classId;
		private String ClassName;
		private String std;
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
			return std;
		}
		public void setStd(String std) {
			this.std = std;
		}
		public ClassDTO(Integer classId, String className, String std) {
			super();
			this.classId = classId;
			ClassName = className;
			this.std = std;
		}
		
		
	}

}
