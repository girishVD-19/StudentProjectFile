package com.example.student.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.student.DTO.MarkDTO;
import com.example.student.DTO.StudentMarkSummaryDTO;
import com.example.student.entity.Gd_Student;
import com.example.student.entity.Gd_Student_Mark;
import com.example.student.entity.Gd_Subject;
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

	public void saveStudentMark(Gd_Student_Mark mark) {
        // Ensure referenced Gd_Student and Gd_Subject are managed (i.e., loaded from DB)
        int studentId = mark.getGd_student().getSTUDENT_ID();  // assuming this getter exists
        int subjectId = mark.getGd_subject().getSUBJECT_ID();  // assuming this getter exists

        Gd_Student student = studentrepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));

        Gd_Subject subject = subjectrepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found with ID: " + subjectId));

        // Set managed references
        mark.setGd_student(student);
        mark.setGd_subject(subject);
        
        if (mark.getMARKS() < 40) {
            mark.setREMARK("Fail");
        } else {
            mark.setREMARK("Pass");
        }

        markRepository.save(mark);
    }
    
    public List<Gd_Student_Mark> findAll() {
        return markRepository.findAll();
    }
    public List<MarkDTO> getAllMarks() {
        List<Gd_Student_Mark> marks = markRepository.findAll();  // Fetching all marks from DB

        // Converting the list of Gd_Student_Mark to MarkDTO
        return marks.stream().map(mark -> new MarkDTO(
                mark.getMARK_ID(),
                mark.getGd_student().getSTUDENT_ID(),
                mark.getGd_student().getNAME(),
                mark.getGd_subject().getSUBJECT_ID(),
                mark.getGd_subject().getSUBJECT_NAME(),
                mark.getMARKS(),
                mark.getREMARK()
        )).collect(Collectors.toList());  // Collecting the results as a list of MarkDTO
    }
    public MarkDTO getMarkById(int id) {
        Gd_Student_Mark mark = markRepository.findById(id).orElse(null);
        if (mark == null) {
            return null;
        }

        return new MarkDTO(
            mark.getMARK_ID(),
            mark.getGd_student().getSTUDENT_ID(),
            mark.getGd_student().getNAME(),
            mark.getGd_subject().getSUBJECT_ID(),
            mark.getGd_subject().getSUBJECT_NAME(),
            mark.getMARKS(),
            mark.getREMARK()
        );
    }
    
    public List<StudentMarkSummaryDTO> getMarksByStudentId(int studentId) {
        List<Gd_Student_Mark> marks = markRepository.findMarksByStudentId(studentId);

        return marks.stream()
                .map(mark -> new StudentMarkSummaryDTO(
                        mark.getGd_subject().getSUBJECT_NAME(),   // assuming getter exists
                        mark.getMARKS(),
                        mark.getREMARK()
                ))
                .collect(Collectors.toList());
    }


    public void deleteMarkById(int id) {
        markRepository.deleteById(id);
    }
}
