package com.example.student.Service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.student.DTO.AssignClassDTO;
import com.example.student.DTO.TeacherRegistarationDTO;
import com.example.student.entity.Gd_Class;
import com.example.student.entity.Gd_Teacher;
import com.example.student.entity.User;
import com.example.student.repository.ClassRepository;
import com.example.student.repository.TeacherRepository;
import com.example.student.repository.UserRepository;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired 
    private ClassRepository classrepository;
    
    @Autowired
   private  PasswordEncoder passwordencoder; 

    public String addTeacher(TeacherRegistarationDTO dto) {
        try {
            // Create and save the teacher
        	          
        	Gd_Teacher teacher = new Gd_Teacher();
        	
            teacher.setName(dto.getName());
            teacher.setEmail(dto.getEmail());
            System.out.println(dto.getName());
            Gd_Teacher savedTeacher = teacherRepository.save(teacher); 

            // Create and save the user
            User user = new User();
            user.setUserId(savedTeacher.getTeacherId());
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
    

    public void assignClassesToTeacher(AssignClassDTO assignClassDTO) {
        // Get the teacher ID and class IDs from the DTO
        Integer teacherId = assignClassDTO.getTeacherId();
        List<Integer> classIds = assignClassDTO.getClassId();

        // Fetch the teacher and classes by their IDs
        Gd_Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        List<Gd_Class> classes = classrepository.findAllById(classIds);
        
        if (classes.size() != classIds.size()) {
            throw new RuntimeException("Some classes not found");
        }
        		

        // Assign classes to teacher
        teacher.setClasses(classes);

        // Assign teacher to classes
        for (Gd_Class cls : classes) {
            cls.getTeachers().add(teacher);
        }

        // Save both teacher and classes
        teacherRepository.save(teacher);
        classrepository.saveAll(classes);
    } 
    
}
