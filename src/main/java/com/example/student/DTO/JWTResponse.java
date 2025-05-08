package com.example.student.DTO;

import java.io.Serializable;

public class JWTResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String token;
    private String username;
    private String message;
    
    
    
//    public String getMessage() {
//		return message;
//	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JWTResponse(String message) {
        this.message = message;
    }

    public JWTResponse(String token, String username) {
        this.token = token;
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
