package com.example.student.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.student.DTO.StudentResponseDTO;
import com.example.student.entity.Gd_Class;
import com.example.student.entity.Gd_Laptop;
import com.example.student.entity.Gd_Student;
import com.example.student.repository.ClassRepository;
import com.example.student.repository.LapTopRepository;
import com.example.student.repository.StudentRepository;

import jakarta.transaction.Transactional;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepository studentrepository;
	@Autowired
	private LapTopRepository laptoprepository;
	@Autowired
	private ClassRepository classrepository;
	
	//TO Get data of all student
	public List<StudentResponseDTO> getAllStudents() {
        List<Gd_Student> students = studentrepository.findAll();
        return students.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
	
   //ToGet Data of particular student data.
	public StudentResponseDTO getStudentById(int studentId) {
        Gd_Student student = studentrepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));

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
	 public String addStudent(Gd_Student student) {
		
		    Gd_Class studentClass = student.getGd_class();
		    if (studentClass == null || studentClass.getCLASS_ID() == null) {
		        throw new IllegalArgumentException("Student must be associated with a valid class.");
		    }

		    Integer classId = studentClass.getCLASS_ID();
		    Gd_Class classFromDb = classrepository.findById(classId)
		            .orElseThrow(() -> new IllegalArgumentException("Class with ID " + classId + " does not exist."));		   
		    Gd_Student savedStudent = studentrepository.save(student);
		    
		   
		    Integer studentId = savedStudent.getSTUDENT_ID(); 
		    String rollNo = String.format("%d%d", studentId, classId);

		      
		    savedStudent.setROLL_NO(Integer.parseInt(rollNo));
		    student.setActive(true);
		  
		    studentrepository.save(savedStudent);
		    Gd_Laptop gdLaptop = student.getGd_laptop();
		    if (gdLaptop == null || gdLaptop.getLAPTOP_ID() == null) {
		        throw new IllegalArgumentException("Student must be assigned a valid laptop.");
		    }
		    Integer laptopId = gdLaptop.getLAPTOP_ID();
		    Gd_Laptop existingLaptop = laptoprepository.findById(laptopId)
		            .orElseThrow(() -> new IllegalArgumentException("Laptop with ID " + laptopId + " does not exist."));

		    if (existingLaptop.getIS_ASSIGNED() == 1) {
		        throw new IllegalArgumentException("Laptop with ID " + laptopId + " is already assigned.");
		    }

		    existingLaptop.setIS_ASSIGNED(1);
		    laptoprepository.save(existingLaptop);

		    student.setGd_class(classFromDb);
		    student.setGd_laptop(existingLaptop);
		    
		    studentrepository.save(student);

		    return "Student added successfully with Roll No: " + rollNo;
	    }
	 
	//to Update Student 
	 @Transactional
	public Gd_Student updateStudent(Integer studentId, Gd_Student student) {
	    Optional<Gd_Student> existingStudentOpt = studentrepository.findById(studentId);
	    if (existingStudentOpt.isEmpty()) {
	        throw new IllegalStateException("Student with ID " + studentId + " does not exist.");
	    }

	    Gd_Student existingStudent = existingStudentOpt.get();
	    // Handle laptop update only if provided
	    if (student.getGd_laptop() != null) {
	        Integer laptopId = student.getGd_laptop().getLAPTOP_ID();
	        Optional<Gd_Laptop> existingLaptopOpt = laptoprepository.findById(laptopId);

	        if (existingLaptopOpt.isEmpty()) {
	            throw new IllegalStateException("Laptop with ID " + laptopId + " does not exist.");
	        }

	        Gd_Laptop laptop = existingLaptopOpt.get();
	        if (laptop.getIS_ASSIGNED() == 1) {
	            throw new IllegalStateException("Laptop with ID " + laptopId + " is already assigned.");
	        }
	        
	        Gd_Laptop oldLaptop = existingStudent.getGd_laptop();
	        if (oldLaptop != null) {
	            oldLaptop.setIS_ASSIGNED(0);
	            laptoprepository.save(oldLaptop);
	        }

	        laptop.setIS_ASSIGNED(1);
	        laptoprepository.save(laptop);
	        existingStudent.setGd_laptop(laptop);
	    }

	    return studentrepository.save(existingStudent);
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
