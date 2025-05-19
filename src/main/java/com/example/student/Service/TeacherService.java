package com.example.student.Service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.student.DTO.TeacherRegistarationDTO;
import com.example.student.entity.Gd_Teacher;
import com.example.student.entity.User;
import com.example.student.repository.TeacherRepository;
import com.example.student.repository.UserRepository;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
   private  PasswordEncoder passwordencoder; 

    public String addTeacher(TeacherRegistarationDTO dto) {
        try {
            // Create and save the teacher
        	          
        	Gd_Teacher teacher = new Gd_Teacher();
        	
            teacher.setName(dto.getName());
            teacher.setEmail(dto.getEmail());
            System.out.println(dto.getName());
           teacherRepository.save(teacher);  // Save teacher to DB

            // Create and save the user
            User user = new User();
            user.setUsername(dto.getName());
            user.setPassword(passwordencoder.encode(dto.getPassword()));
            user.setRoles(Arrays.asList("TEACHER"));
            userRepository.save(user);  // Save user to DB

            return "Teacher registered successfully!";
        } catch (Exception e) {
            // Log the error
            e.printStackTrace();  // Or use a logger for better error handling
            return null;  // Return null in case of error
        }
    }
}
