package com.example.student.DTO;

import java.util.Objects;

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
		
		@Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (!(o instanceof SubjectDTO)) return false;
	        SubjectDTO that = (SubjectDTO) o;
	        return Objects.equals(id, that.id) &&
	               Objects.equals(name, that.name);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(id, name);
	    }
		
		
	    
	    
}
