package com.example.student.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.student.DTO.ClassDetailsDTO;
import com.example.student.DTO.ClassResponseDTO;
import com.example.student.DTO.ClassSummary;
import com.example.student.DTO.ClassWithStudentDTO;
import com.example.student.DTO.PageSortDTO;
import com.example.student.Service.ClassService;
import com.example.student.entity.Gd_Class;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("class")
public class ClassController {
	
	@Autowired
	private ClassService classservice;
	
	@Operation(
			summary="To Get the data of a Whole Class" ,
			description="To Get the data of a Whole Class"
			)
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/")
	public PageSortDTO<ClassDetailsDTO> getClassDetails(
	        @RequestParam(required = false) String std,
	        @PageableDefault(size = 10) Pageable pageable) {

	    Page<ClassDetailsDTO> page;
	    if (std == null || std.isEmpty()) {
	        page = classservice.getAllClassDetails(pageable);
	    } else {
	        page = classservice.getClassDetailsWithRoom(std, pageable);
	    }

	    return classservice.convertToPageSortDTO(page);
	}

     @Operation(
    		 summary="To Get Details of the Specific Class Id",
    		 description="To get the details of the Specific Class Id"
    		 )
     @GetMapping("/{classId}")
     public ResponseEntity<ClassSummary> getClassDetailsById(@PathVariable Integer classId) {
         try {
             // Logging the incoming classId for debugging
             System.out.println("Received classId: " + classId);

             ClassSummary classSummary = classservice.getClassDetailsById(classId);

             if (classSummary != null) {
                 return ResponseEntity.ok(classSummary);
             } else {
                 return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
             }
         } catch (Exception e) {
             // Print stack trace for further debugging
             e.printStackTrace();
             return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
         }
     }
     
     @Operation(
    		 summary="To Get Details of the Students with Class",
    		 description="To Get Details of the Students with Class"
    		 )
	 @GetMapping("/{classId}/details")
	    public ResponseEntity<ClassWithStudentDTO> getClassDetailsWithStudent(@PathVariable Integer classId) {
	        ClassWithStudentDTO classDetails = classservice.getClassWithStudents(classId);
	        return ResponseEntity.ok(classDetails);
	 }
	 
     @Operation(
    		 summary="To Add the Class Data.",
    		 description="To Send the class Details to  the DataBase."
               ) 
	 @PostMapping("/Add")
     public Gd_Class createClass(@RequestBody Gd_Class gdClass) {
         return classservice.saveClass(gdClass);
     }
     
@Operation(summary="To Update the the Class Room",
description="To update the class room details"
)
     @PatchMapping("update/{id}")
     public ResponseEntity<String> updateClassRoomFromJson(
             @PathVariable Integer id,
             @RequestBody Map<String, Integer> requestBody) {

         Integer roomId = requestBody.get("roomId");
         if (roomId == null) {
             return ResponseEntity.badRequest().body("Missing roomId in request body.");
         }

         try {
             classservice.updateClassRoom(id, roomId);
             return ResponseEntity.ok("Room updated successfully.");
         } catch (IllegalStateException e) {
             return ResponseEntity.badRequest().body(e.getMessage());  // e.g., "Room is not active"
         } catch (RuntimeException e) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
         }
     }

    @DeleteMapping("/{id}")
    @Operation(
   		 summary="To delete the Class Details.",
   		 description="To delete the Class Details With the given class id."
              ) 
    public ResponseEntity<Void> deleteClass(@PathVariable Integer id) {
        classservice.deleteGdClass(id);
        return ResponseEntity.noContent().build();
    }

}
