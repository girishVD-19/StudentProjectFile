package com.example.student.controller;

import com.example.student.DTO.JWTRequest;
import com.example.student.DTO.JWTResponse;
import com.example.student.config.BlacklistToken;
import com.example.student.config.JwtHelper;
import com.example.student.entity.User;
import com.example.student.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private  BlacklistToken blacklisttoken;

    //  LOGIN endpoint
    @Operation(summary="To Login the user")
    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody JWTRequest request) {
        try {
            authenticate(request.getUsername(), request.getPassword());

            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            Integer userId = user.getUserId();
            System.out.println("userId;"+userId);// ✅ Correct way to get userId

            String token = jwtHelper.generateToken(userDetails,userId);

            JWTResponse response = new JWTResponse(token, userDetails.getUsername());
            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            logger.error("Login failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(new JWTResponse("Invalid username or password"));
        }
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody JWTRequest request) {
        // 1. Basic validation
        if (request.getUsername() == null || request.getUsername().isBlank() ||
            request.getPassword() == null || request.getPassword().isBlank()) {
            return ResponseEntity.badRequest().body("Username and password must not be null or empty");
        }

        // 2. Check if username already exists
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        // 3. Assign default role if none provided
        List<String> roles = request.getRoles();
        if (roles == null || roles.isEmpty()) {
            roles = List.of("USER");  // default role
        }

        // 4. Create and save user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(roles);  // Set the roles

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    
    //Logout Feature
    @Operation(summary="to Logout form the system")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            
            blacklisttoken.blacklist(token);
           
            logger.info("Logout requested for token: {}", token);
        }

        SecurityContextHolder.clearContext();  // Remove auth info from context
        return ResponseEntity.ok("Logged out successfully");
    }

 
    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
    
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException() {
        return new ResponseEntity<>("Invalid username or password!", HttpStatus.UNAUTHORIZED);
    }
}
