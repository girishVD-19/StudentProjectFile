package com.example.student.DTO;

import com.example.student.entity.Gd_Class;
import com.example.student.entity.Gd_Laptop;

public class StudentDTO {
	private Integer rollNo; // rollNo is now Integer
    private String name;
    private String city;
    private ClassDetailsDTO classDetails;
    private LaptopDTO laptopDetails;

    // Constructor
    public StudentDTO(Integer rollNo, String name, String city, ClassDetailsDTO classDetails, LaptopDTO laptopDetails) {
        this.rollNo = rollNo;
        this.name = name;
        this.city = city;
        this.classDetails = classDetails;
        this.laptopDetails = laptopDetails;
    }

    // Getters and Setters
    public Integer getRollNo() {
        return rollNo;
    }

    public void setRollNo(Integer rollNo) {
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public ClassDetailsDTO getClassDetails() {
        return classDetails;
    }

    public void setClassDetails(ClassDetailsDTO classDetails) {
        this.classDetails = classDetails;
    }

    public LaptopDTO getLaptopDetails() {
        return laptopDetails;
    }

    public void setLaptopDetails(LaptopDTO laptopDetails) {
        this.laptopDetails = laptopDetails;
    }
}



