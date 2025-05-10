package com.example.student.DTO;

import io.jsonwebtoken.lang.Objects;

public class SubjectDTO {
	 private int id;
	    private String name;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public SubjectDTO(int id, String name) {
			super();
			this.id = id;
			this.name = name;
		}
		
		
	    
	    
}
