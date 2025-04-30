package com.example.student.DTO;

import java.util.List;

public class LaptopDTO {
	 private Integer laptopId;
	    private int modelNo;
	    private boolean assigned;
	    private List<Integer> studentIds;  
	    // This will hold the studentDTO with studentId
	    
	    public LaptopDTO() {
	    	
	    }
	    
		public LaptopDTO(Integer laptopId, int modelNo, int isAssigned, List<Integer> studentIds) {
	        this.laptopId = laptopId;
	        this.modelNo = modelNo;
	        this.assigned = (isAssigned == 1); 
	        this.studentIds = studentIds;  
	    }
	
		public LaptopDTO(Integer laptopId,  boolean assigned, int modelNo) {
			this.laptopId=laptopId;
			this.assigned=assigned;
			this.modelNo=modelNo;
		
		}
			
		public Integer getLaptopId() {
			return laptopId;
		}
		public void setLaptopId(Integer laptopId) {
			this.laptopId = laptopId;
		}
		public int getModelNo() {
			return modelNo;
		}
		public void setModelNo(int modelNo) {
			this.modelNo = modelNo;
		}
		public boolean isAssigned() {
			return assigned;
		}
		public void setAssigned(boolean assigned) {
			this.assigned = assigned;
		}
		public List<Integer> getStudentIds() {
			return studentIds;
		}
		public void setStudentIds(List<Integer> studentIds) {
			this.studentIds = studentIds;
		}

	    
}
