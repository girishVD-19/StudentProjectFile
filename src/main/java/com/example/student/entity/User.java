package com.example.student.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="GD_USER")
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name="NAME")
    private String username;

    @Column(name="PASSWORD")
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "GD_USER_ROLES", joinColumns = @JoinColumn(name = "userId"))
    @Column(name = "role")
    private List<String> roles;


    // Default constructor for JPA
    public User() {
    }

    // Constructor for creating User object
    public User(String username, String password, List<String> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    // Implementing UserDetails methods

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles == null) return List.of(); // Avoid null roles
        return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Adjust according to your business logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Adjust according to your business logic
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Adjust according to your business logic
    }

    @Override
    public boolean isEnabled() {
        return true;  // Adjust according to your business logic
    }

    // Override toString() for better representation (optional)
    @Override
    public String toString() {
        return "User{userId=" + userId + ", username='" + username + "', roles=" + roles + "}";
    }
}
