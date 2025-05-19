package com.example.student.DTO;

public class StudentRegistrationDTO {
    private String name;
    private String city;
    private String password;
    private ClassDetailsDTO gd_class;  // Nested DTO
    private LaptopDTO gd_laptop;      // Nested DTO

    // Getters and Setters
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ClassDetailsDTO getGd_class() {
        return gd_class;
    }

    public void setGd_class(ClassDetailsDTO gd_class) {
        this.gd_class = gd_class;
    }

    public LaptopDTO getGd_laptop() {
        return gd_laptop;
    }

    public void setGd_laptop(LaptopDTO gd_laptop) {
        this.gd_laptop = gd_laptop;
    }
}
