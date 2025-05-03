package com.example.student.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.student.DTO.MarkDTO;
import com.example.student.DTO.StudentMarkDTO;
import com.example.student.DTO.StudentMarkSummaryDTO;
import com.example.student.entity.Gd_Student;
import com.example.student.entity.Gd_Student_Mark;
import com.example.student.entity.Gd_Subject;
import com.example.student.entity.Gd_Subject_Mapping;
import com.example.student.repository.StudentMarkRepository;
import com.example.student.repository.StudentRepository;
import com.example.student.repository.SubjectRepository;

@Service
public class StudentMarkService {
 
	@Autowired
    private StudentMarkRepository markRepository;
	
	@Autowired
	private StudentRepository studentrepository;
	
	@Autowired
	private SubjectRepository subjectrepository;
	
	@Autowired
	private StudentMarkRepository SMrepository;

	public void saveStudentMark(Gd_Student_Mark mark) {
        // Ensure referenced Gd_Student and Gd_Subject are managed (i.e., loaded from DB)
        int studentId = mark.getGd_student().getSTUDENT_ID();  // assuming this getter exists
        Gd_Student student = studentrepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));

        // Set managed references
        mark.setGd_student(student);
       
        if (mark.getMARKS() < 40) {
            mark.setREMARK("Fail");
        } else {
            mark.setREMARK("Pass");
        }

        markRepository.save(mark);
    }
    
	public List<MarkDTO> getAllMarks() {
	    List<Gd_Student_Mark> marks = markRepository.findAll();  // Fetching all marks from DB
	    List<MarkDTO> markDTOs = new ArrayList<>();

	    for (Gd_Student_Mark mark : marks) {
	        Gd_Student student = mark.getGd_student();
	        Gd_Subject_Mapping subjectMapping = mark.getGd_subject_mapping();
	        Gd_Subject subject = subjectMapping != null ? subjectMapping.getGd_subject() : null;

	        markDTOs.add(new MarkDTO(
	            mark.getMARK_ID(),
	            student != null ? student.getSTUDENT_ID() : 0,
	            student != null ? student.getNAME() : null,
	            subject != null ? subject.getSUBJECT_ID() : 0,
	            subject != null ? subject.getSUBJECT_NAME() : null,
	            mark.getMARKS(),
	            mark.getREMARK()
	        ));
	    }

	    return markDTOs;
	}

    public MarkDTO getMarkById(int id) {
        Gd_Student_Mark mark = markRepository.findById(id).orElse(null);
        if (mark == null) return null;

        Gd_Student student = mark.getGd_student();
        Gd_Subject_Mapping subjectMapping = mark.getGd_subject_mapping();
        Gd_Subject subject = subjectMapping != null ? subjectMapping.getGd_subject() : null;

        return new MarkDTO(
            mark.getMARK_ID(),
            student != null ? student.getSTUDENT_ID() : 0,
            student != null ? student.getNAME() : null,
            subject != null ? subject.getSUBJECT_ID() : 0,
            subject != null ? subject.getSUBJECT_NAME() : null,
            mark.getMARKS(),
            mark.getREMARK()
        );
    }

    public List<MarkDTO> getMarksByStudentId(int studentId) {
        List<Gd_Student_Mark> marks = markRepository.findMarksByStudentId(studentId);  // Fetching marks by student ID from DB
        List<MarkDTO> markDTOs = new ArrayList<>();

        for (Gd_Student_Mark mark : marks) {
            Gd_Student student = mark.getGd_student();
            Gd_Subject_Mapping subjectMapping = mark.getGd_subject_mapping();
            Gd_Subject subject = subjectMapping != null ? subjectMapping.getGd_subject() : null;

            markDTOs.add(new MarkDTO(
                mark.getMARK_ID(),
                student != null ? student.getSTUDENT_ID() : 0,
                student != null ? student.getNAME() : null,
                subject != null ? subject.getSUBJECT_ID() : 0,
                subject != null ? subject.getSUBJECT_NAME() : null,
                mark.getMARKS(),
                mark.getREMARK()
            ));
        }

        return markDTOs;
    }
    
    
    public List<MarkDTO> getMarksByStudentIdAndClassId(int studentId, int classId) {
        List<Gd_Student_Mark> marks = markRepository.findMarksByStudentIdAndClassId(studentId, classId);
        List<MarkDTO> markDTOs = new ArrayList<>();

        for (Gd_Student_Mark mark : marks) {
            Gd_Student student = mark.getGd_student();
            Gd_Subject_Mapping subjectMapping = mark.getGd_subject_mapping();
            Gd_Subject subject = subjectMapping != null ? subjectMapping.getGd_subject() : null;

            markDTOs.add(new MarkDTO(
                mark.getMARK_ID(),
                student != null ? student.getSTUDENT_ID() : 0,
                student != null ? student.getNAME() : null,
                subject != null ? subject.getSUBJECT_ID() : 0,
                subject != null ? subject.getSUBJECT_NAME() : null,
                mark.getMARKS(),
                mark.getREMARK()
            ));
        }

        return markDTOs;
    }

    
  
    public List<StudentMarkDTO> getStudentMarks(int studentId, int srNo) {
        List<Object[]> results = markRepository.findStudentMarksNative(studentId, srNo);
        List<StudentMarkDTO> dtos = new ArrayList<>();
        for (Object[] row : results) {
            dtos.add(new StudentMarkDTO(
                (String) row[0], // studentName
                (String) row[1], // subjectName
                (String) row[2], // className
                (Integer) row[3], // marks
                (String) row[4]   // remark
            ));
        }
        return dtos;
    }


   
    

    



    public void deleteMarkById(int id) {
        markRepository.deleteById(id);
    }
}
