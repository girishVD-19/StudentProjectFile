package com.example.student.DTO;

public class LaptopDTO {
    private int laptopId;
    private boolean isAssigned; // True or False
    private int modelNo;
    
    public LaptopDTO() {
    	
    }
	public LaptopDTO(int laptopId, boolean isAssigned, int modelNo) {
		super();
		this.laptopId = laptopId;
		this.isAssigned = isAssigned;
		this.modelNo = modelNo;
	}
	public int getLaptopId() {
		return laptopId;
	}
	public void setLaptopId(int laptopId) {
		this.laptopId = laptopId;
	}
	public boolean isAssigned() {
		return isAssigned;
	}
	public void setAssigned(boolean isAssigned) {
		this.isAssigned = isAssigned;
	}
	public int getModelNo() {
		return modelNo;
	}
	public void setModelNo(int modelNo) {
		this.modelNo = modelNo;
	}

}
