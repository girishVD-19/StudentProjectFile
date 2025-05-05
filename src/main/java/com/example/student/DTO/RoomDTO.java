package com.example.student.DTO;

import java.util.List;

public class RoomDTO {
	
	private Integer room_id;
	private Integer capacity;
	 private List<ClassDTO> classes;
	 
	 public static class ClassDTO {
		    private Integer classId;
		    private String className;
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
			public ClassDTO(Integer classId, String className) {
				super();
				this.classId = classId;
				this.className = className;
			}
		 
		}
	
	public RoomDTO() {
		
	}
	
	public Integer getRoom_id() {
		return room_id;
	}
//	public RoomDTO(Integer room_id, Integer capacity, Integer classId, String className) {
//        this.room_id = room_id;
//        this.capacity = capacity;
//        // Add the class details as a ClassDTO object
//        this.classes = List.of(new ClassDTO(classId, className));
//    }
	
	 public RoomDTO(Integer room_id, Integer capacity, List<ClassDTO> classes) {
	        this.room_id = room_id;
	        this.capacity = capacity;
	        this.classes = classes;
	    }
	public List<ClassDTO> getClasses() {
		return classes;
	}
	public void setClasses(List<ClassDTO> classes) {
		this.classes = classes;
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

}

