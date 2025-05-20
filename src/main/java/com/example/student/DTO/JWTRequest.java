package com.example.student.DTO;

import java.io.Serializable;
import java.util.List;

public class JWTRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private Integer userId;
    private List<String> Roles;

    public JWTRequest() {
        // Default constructor
    }

    
    public Integer getUserId() {
		return userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	public JWTRequest(String username, String password,List<String> roles) {
        this.username = username;
        this.password = password;
        this.Roles=roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public List<String> getRoles() {
		return Roles;
	}

	public void setRoles(List<String> roles) {
		Roles = roles;
	}
    
    
}
