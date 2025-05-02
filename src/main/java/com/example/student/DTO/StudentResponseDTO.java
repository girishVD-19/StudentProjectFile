package com.example.student.DTO;

public class StudentResponseDTO {
	
	private int rollNo;
    private String name;
    private String city;
    private ClassInfo classDetails;
    private LaptopInfo laptopDetails;

    public static class ClassInfo {
        private int classId;
        private String className;
        private String std;

        // Getters and Setters
        public int getClassId() { return classId; }
        public void setClassId(int classId) { this.classId = classId; }

        public String getClassName() { return className; }
        public void setClassName(String className) { this.className = className; }

        public String getStd() { return std; }
        public void setStd(String std) { this.std = std; }
    }

    public static class LaptopInfo {
        private int laptopId;

        // Getters and Setters
        public int getLaptopId() { return laptopId; }
        public void setLaptopId(int laptopId) { this.laptopId = laptopId; }
    }

    // Getters and Setters
    public int getRollNo() { return rollNo; }
    public void setRollNo(int rollNo) { this.rollNo = rollNo; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public ClassInfo getClassDetails() { return classDetails; }
    public void setClassDetails(ClassInfo classDetails) { this.classDetails = classDetails; }

    public LaptopInfo getLaptopDetails() { return laptopDetails; }
    public void setLaptopDetails(LaptopInfo laptopDetails) { this.laptopDetails = laptopDetails; }

}
