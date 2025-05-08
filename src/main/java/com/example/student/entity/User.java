package com.example.student.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="GD_USER")
public class User implements UserDetails {
 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	
	@Column(name="NAME")
	private String username;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles;

	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name="PASSWORD")
	private String password;
	
	
    private Collection<? extends GrantedAuthority> authorities;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles == null) return List.of(); // <- this avoids null
        return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
    }
    
    // Roles/permissions associated with the user

    // Constructor for creating User object
   
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	// Getters
    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Assuming account is always valid, adjust as needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Assuming account is not locked, adjust as needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Assuming credentials are not expired, adjust as needed
    }

    @Override
    public boolean isEnabled() {
        return true;  // Assuming account is always enabled, adjust as needed
    }
}
