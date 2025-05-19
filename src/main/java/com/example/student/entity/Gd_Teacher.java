package com.example.student.entity;

import java.io.Serializable;
import java.util.List;

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
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
   

    
    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToMany
    @JoinTable(
        name = "GD_TEACHER_CLASS", // This is the name of the join table
        joinColumns = @JoinColumn(name = "TEACHER_ID"), // The foreign key column for the teacher
        inverseJoinColumns = @JoinColumn(name = "CLASS_ID") // The foreign key column for the class
    )
    private List<Gd_Class> classes;


	public List<Gd_Class> getClasses() {
		return classes;
	}

	public void setClasses(List<Gd_Class> classes) {
		this.classes = classes;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

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
