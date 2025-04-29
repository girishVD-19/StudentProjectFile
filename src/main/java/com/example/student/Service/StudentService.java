package com.example.student.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.student.DTO.ClassDetailsDTO;
import com.example.student.DTO.LaptopDTO;
import com.example.student.DTO.StudentDTO;
import com.example.student.entity.Gd_Class;
import com.example.student.entity.Gd_Laptop;
import com.example.student.entity.Gd_Student;
import com.example.student.repository.ClassRepository;
import com.example.student.repository.LapTopRepository;
import com.example.student.repository.StudentRepository;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepository studentrepository;
	@Autowired
	private LapTopRepository laptoprepository;
	@Autowired
	private ClassRepository classrepository;
	
	//TO Get data of all student
	public List<StudentDTO> getAllStudents() {
        // Fetch all students from the repository
		 List<Gd_Student> students = studentrepository.findAll();

	        // Filter only active students
	        return students.stream()
	                .filter(student -> student.isActive())  // Only include active students
	                .map(student -> {
	                    Gd_Class gdClass = student.getGd_class();
	                    ClassDetailsDTO classDetails = null;
	                    if (gdClass != null) {
	                        classDetails = new ClassDetailsDTO(
	                            gdClass.getCLASS_ID(),
	                            gdClass.getCLASS_NAME(),
	                            gdClass.getSTD()
	                        );
	                    }

	                    Gd_Laptop gdLaptop = student.getGd_laptop();
	                    LaptopDTO laptopDetails = null;
	                    if (gdLaptop != null) {
	                        laptopDetails = new LaptopDTO(
	                            gdLaptop.getLAPTOP_ID(),
	                            gdLaptop.getIS_ASSIGNED() == 1,
	                            gdLaptop.getMODEL_NO()
	                        );
	                    }

	                    // Return the mapped StudentDTO
	                    return new StudentDTO(
	                        student.getROLL_NO(),
	                        student.getNAME(),
	                        student.getCITY(),
	                        classDetails,
	                        laptopDetails
	                    );
	                })
	                .collect(Collectors.toList());
    }
	
	
   //ToGet Data of particular student data.
	 public StudentDTO getStudentById(int Id) {
		 Optional<Gd_Student> optionalStudent = studentrepository.findById(Id);

	        // If the student exists, check if the student is active and then map the details to StudentDTO
	        if (optionalStudent.isPresent()) {
	            Gd_Student student = optionalStudent.get();

	            // Check if the student is active
	            if (!student.isActive()) {
	               return null;
	            }
	            // Get the Class details for the student
	            Gd_Class gdClass = student.getGd_class();
	            ClassDetailsDTO classDetails = null;
	            if (gdClass != null) {
	                classDetails = new ClassDetailsDTO(
	                    gdClass.getCLASS_ID(),
	                    gdClass.getCLASS_NAME(),
	                    gdClass.getSTD()  // Include only classId, className, and std
	                );
	            }

	            // Get the Laptop details for the student
	            Gd_Laptop gdLaptop = student.getGd_laptop();
	            LaptopDTO laptopDetails = null;
	            if (gdLaptop != null) {
	                laptopDetails = new LaptopDTO(
	                    gdLaptop.getLAPTOP_ID(),
	                    gdLaptop.getIS_ASSIGNED() == 1,
	                    gdLaptop.getMODEL_NO()
	                );
	            }

	            // Return the StudentDTO containing student, class, and laptop details
	            return new StudentDTO(
	                student.getROLL_NO(),
	                student.getNAME(),
	                student.getCITY(),
	                classDetails,  // Class details (including classId, className, and STD)
	                laptopDetails  // Laptop details (including laptopId, isAssigned, modelNo)
	            );
	        } else {
	            // If student is not found, throw exception
	            throw new IllegalStateException("Student with ID " + Id + " not found.");
	        }
	    }
	    
   //To Add Student
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
