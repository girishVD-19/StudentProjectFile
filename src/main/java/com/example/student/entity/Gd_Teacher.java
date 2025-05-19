package com.example.student.entity;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "GD_TEACHER")
public class Gd_Teacher{

  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEACHER_ID")
    private int teacherId;

    
    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    public Gd_Teacher() {
    }

    public Gd_Teacher(Integer teacherId,String name, String email) {
    	this.teacherId=teacherId;
        this.name = name;
        this.email = email;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Gd_Teacher [teacherId=" + teacherId + ", name=" + name + ", email=" + email + "]";
    }
}
