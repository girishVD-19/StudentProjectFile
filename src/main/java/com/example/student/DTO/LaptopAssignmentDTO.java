package com.example.student.DTO;

public class LaptopAssignmentDTO {

	private Integer laptopId;
    private Integer studentId;
	public Integer getLaptopId() {
		return laptopId;
	}
	public void setLaptopId(Integer laptopId) {
		this.laptopId = laptopId;
	}
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public LaptopAssignmentDTO(Integer laptopId, Integer studentId) {
		super();
		this.laptopId = laptopId;
		this.studentId = studentId;
	}
    
    
}
