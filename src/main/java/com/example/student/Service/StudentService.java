package com.example.student.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import com.example.student.config.SecurityConfig;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.student.DTO.PageInfoDTO;
import com.example.student.DTO.StudentListResponseDTO;
import com.example.student.DTO.StudentRegistrationDTO;
import com.example.student.DTO.StudentResponseDTO;
import com.example.student.entity.Gd_Class;
import com.example.student.entity.Gd_Laptop;
import com.example.student.entity.Gd_Laptop_History;
import com.example.student.entity.Gd_Student;
import com.example.student.entity.User;
import com.example.student.repository.ClassRepository;
import com.example.student.repository.LapTopRepository;
import com.example.student.repository.LaptopHistoryRepository;
import com.example.student.repository.StudentRepository;
import com.example.student.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.transaction.Transactional;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepository studentrepository;
	@Autowired
	private LapTopRepository laptoprepository;
	@Autowired
	private ClassRepository classrepository;
	
	@Autowired
	private LaptopHistoryRepository laptopHistoryRepository;
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	

	
	//TO Get data of all student
	public StudentListResponseDTO getAllStudents(String name, String city, Pageable pageable) {
	    Page<Gd_Student> studentPage = studentrepository.findByFilters(name, city, pageable);

	    List<StudentResponseDTO> result = studentPage.getContent().stream()
	            .filter(Gd_Student::isActive)
	            .map(this::mapToDTO)  // Convert Gd_Student to DTO
	            .collect(Collectors.toList());

	    // Prepare pagination info
	    PageInfoDTO pageInfo = new PageInfoDTO();
	    pageInfo.setTotalRecords(studentPage.getTotalElements());
	    pageInfo.setCurrentPageNo(studentPage.getNumber() + 1);
	    pageInfo.setPageSize(studentPage.getSize());

	    StudentListResponseDTO response = new StudentListResponseDTO();
	    response.setResult(result);
	    response.setPageInfo(pageInfo);
	    return response;
	}

	
	   

	    
   //ToGet Data of particular student data.
	public StudentResponseDTO getStudentById(int studentId) {
		Gd_Student student = studentrepository.findById(studentId)
	            .filter(Gd_Student::isActive) // Only allow if isActive == true
	            .orElseThrow(() -> new IllegalArgumentException("Active student not found with ID: " + studentId));

	    return mapToDTO(student);
    }

    private StudentResponseDTO mapToDTO(Gd_Student student) {
        StudentResponseDTO dto = new StudentResponseDTO();
        dto.setRollNo(student.getROLL_NO());
        dto.setName(student.getNAME());
        dto.setCity(student.getCITY());

        // Class Info
        Gd_Class gdClass = student.getGd_class();
        if (gdClass != null) {
            StudentResponseDTO.ClassInfo classInfo = new StudentResponseDTO.ClassInfo();
            classInfo.setClassId(gdClass.getCLASS_ID());
            classInfo.setClassName(gdClass.getCLASS_NAME());
            classInfo.setStd(gdClass.getSTD());
            dto.setClassDetails(classInfo);
        }

        // Laptop Info
        Gd_Laptop laptop = student.getGd_laptop();
        if (laptop != null) {
            StudentResponseDTO.LaptopInfo laptopInfo = new StudentResponseDTO.LaptopInfo();
            laptopInfo.setLaptopId(laptop.getLAPTOP_ID());
            dto.setLaptopDetails(laptopInfo);
        }

        return dto;
    }
	    
   //To Add Student
    @Transactional
    public String addStudent(StudentRegistrationDTO dto) {
        // Create and populate Gd_Student
        Gd_Student student = new Gd_Student();
        student.setNAME(dto.getName());
        student.setCITY(dto.getCity());
        student.setActive(true);

        // Fetch Class by ID
        Gd_Class gdClass = classrepository.findById(dto.getGd_class().getClassId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid class ID"));

        // Fetch Laptop by ID
        Gd_Laptop gdLaptop = laptoprepository.findById(dto.getGd_laptop().getLaptopId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid laptop ID"));

        if (!gdLaptop.getIS_ALIVE() || gdLaptop.getIS_ASSIGNED() == 1) {
            throw new IllegalArgumentException("Laptop is not available.");
        }

        // Assign Laptop and Class to student
        gdLaptop.setIS_ASSIGNED(1);
        laptoprepository.save(gdLaptop);

        student.setGd_class(gdClass);
        student.setGd_laptop(gdLaptop);

        // Save student
        Gd_Student savedStudent = studentrepository.save(student);

        // Generate Roll No
        Integer studentId = savedStudent.getSTUDENT_ID();
        String rollNo = String.format("%d%d", studentId, dto.getGd_class().getClassId());
        savedStudent.setROLL_NO(Integer.parseInt(rollNo));

        studentrepository.save(savedStudent);

        // Create Laptop History
        // Implement this based on your logic if needed
  
        // Create User with ROLE_USER
        User user = new User();
        user.setUserId(studentId);
        user.setUsername(dto.getName());
        user.setPassword(passwordEncoder.encode(dto.getPassword())); 
        user.setRoles(List.of("USER"));

        userRepository.save(user);

        return "Student and user added successfully with Roll No: " + rollNo;
    }
    
    @Transactional
    public Object manageStudent(Integer studentId, Gd_Student student, String action) {
        switch (action.toLowerCase()) {
            case "update":
                return updateStudent(studentId, student);

            case "deactivate":
                return deactivateStudentAndReleaseLaptop(studentId);

            case "activate":
                return activateStudentAndUpdateStatus(studentId);

            default:
                throw new IllegalArgumentException("Invalid action: " + action);
        }
    }
	 
	//to Update Student 
	 @Transactional
	 public String updateStudent(Integer studentId, Gd_Student student) {
		 Optional<Gd_Student> existingStudentOpt = studentrepository.findById(studentId);
		    if (existingStudentOpt.isEmpty()) {
		        // Return a simple error message instead of throwing exception
		        return "Student with ID " + studentId + " not found.";
		    }

		    Gd_Student existingStudent = existingStudentOpt.get();
		    Gd_Laptop newLaptop = null;

		    if (student.getGd_laptop() != null) {
		        Integer newLaptopId = student.getGd_laptop().getLAPTOP_ID();
		        Optional<Gd_Laptop> existingLaptopOpt = laptoprepository.findById(newLaptopId);

		        if (existingLaptopOpt.isEmpty()) {
		            return "Laptop with ID " + newLaptopId + " not found.";
		        }

		        newLaptop = existingLaptopOpt.get();

		        if (newLaptop.getIS_ASSIGNED() == 1) {
		            return "Laptop with ID " + newLaptopId + " is already assigned.";
		        }

		        // Unassign old laptop
		        Gd_Laptop oldLaptop = existingStudent.getGd_laptop();
		        if (oldLaptop != null) {
		            oldLaptop.setIS_ASSIGNED(0);
		            laptoprepository.save(oldLaptop);
		        }

		        newLaptop.setIS_ASSIGNED(1);
		        laptoprepository.save(newLaptop);
		        existingStudent.setGd_laptop(newLaptop);

		        // Close old history
		        Optional<Gd_Laptop_History> existingHistoryOpt = laptopHistoryRepository
		                .findActiveHistoryByStudentId(existingStudent.getSTUDENT_ID());

		        existingHistoryOpt.ifPresent(history -> {
		            history.setReturn_Date(LocalDate.now());
		            laptopHistoryRepository.save(history);
		        });

		        // Create new history
		        Gd_Laptop_History newHistory = new Gd_Laptop_History(
		                newLaptop,
		                existingStudent,
		                LocalDate.now(),
		                null
		        );
		        laptopHistoryRepository.save(newHistory);
		    }

		    studentrepository.save(existingStudent);
		    return "Student updated successfully.";
	 }

	 
	 @Transactional
	 public String deactivateStudentAndReleaseLaptop(Integer studentId) {
		 Gd_Student student = studentrepository.findById(studentId)
			        .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));

			    student.setActive(false);

			    Gd_Laptop laptop = student.getGd_laptop();
			    if (laptop != null && laptop.getIS_ASSIGNED() == 1) {
			        laptop.setIS_ASSIGNED(0);
			        laptoprepository.save(laptop);
			        student.setGd_laptop(null);
			    }

			    laptopHistoryRepository.findActiveHistoryByStudentId(studentId).ifPresent(history -> {
			        history.setReturn_Date(LocalDate.now());
			        laptopHistoryRepository.save(history);
			    });

			    studentrepository.save(student);
			    return "Student deactivated, laptop released, and history updated successfully.";
			}
	 

	 @Transactional
	 public String activateStudentAndUpdateStatus(Integer studentId) {
	     Gd_Student student = studentrepository.findById(studentId)
	         .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));

	     if (student.isActive()) {
	         return "Student is already active.";
	     }

	     // Activate the student
	     student.setActive(true);

	     // Reassign laptop if it exists and is currently unassigned
	     Gd_Laptop laptop = student.getGd_laptop();
	     if (laptop != null && laptop.getIS_ASSIGNED() == 0) {
	         laptop.setIS_ASSIGNED(1);
	         laptoprepository.save(laptop);

	         // Create a new laptop history record
	         Gd_Laptop_History newHistory = new Gd_Laptop_History();
	         newHistory.setGd_student(student);
	         newHistory.setGd_laptop(laptop);
	         newHistory.setASSIGNED_DATE(LocalDate.now());
	         newHistory.setReturn_Date(null); // still active

	         laptopHistoryRepository.save(newHistory);
	     }

	     studentrepository.save(student);
	     return "Student activated. Laptop reassigned and history created (if applicable).";
	 }

	//Delete the student
	public String deleteStudentById(int id) {
	    Optional<Gd_Student> optionalStudent = studentrepository.findById(id);
	    
	    if (optionalStudent.isEmpty()) {
	        throw new NoSuchElementException("Student with ID " + id + " does not exist.");
	    }

	    Gd_Student student = optionalStudent.get();

	    // Optional: Unassign the laptop before deleting the student
	    Gd_Laptop laptop = student.getGd_laptop();
	    if (laptop != null) {
	        laptop.setIS_ASSIGNED(0); // Mark laptop as available
	        laptoprepository.save(laptop);
	    }
	    studentrepository.deleteById(id);
	    return "Student with ID " + id + " deleted successfully.";
	}
}
