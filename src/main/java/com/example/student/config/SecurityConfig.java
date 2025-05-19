package com.example.student.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.student.Service.CustomUserDetailsService;



@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http,
	                                               JwtAuthenticationFilter jwtAuthenticationFilter,
	                                               JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) throws Exception {
	    http
	        .csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(authz -> authz
	            // Permit these paths without authentication
	            .requestMatchers("/auth/login", "/teacher/register",  "/Student/auth/register", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
	            
	            // Secure logout path (requires authentication)
	            .requestMatchers("/auth/logout").authenticated()

	            // Only accessible by users with "ADMIN" role
	            .requestMatchers("/admin/**").hasRole("ADMIN")
	          

	            // Accessible by both "USER" and "ADMIN"
	            .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
	            
	            // Finally, require authentication for all other requests
	            .anyRequest().authenticated()
	        )
	        .exceptionHandling(ex -> ex
	            .authenticationEntryPoint(jwtAuthenticationEntryPoint) // Custom entry point for authentication errors
	        )
	        .sessionManagement(session -> session
	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless session management (for JWT authentication)
	        )
	        .authenticationProvider(authenticationProvider()) // Your authentication provider
	        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter before UsernamePasswordAuthenticationFilter

	    return http.build();
	}

	
	@Bean
	public AuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	    provider.setUserDetailsService(customUserDetailsService);
	    provider.setPasswordEncoder(passwordEncoder());
	    return provider;
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
	    return http.getSharedObject(AuthenticationManagerBuilder.class)
	            .userDetailsService(customUserDetailsService)
	            .passwordEncoder(passwordEncoder()).and()
	            .build();
	}

    

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    
   
}
