package com.example.student.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.student.DTO.MarkDTO;
import com.example.student.entity.Gd_Student_Mark;
import com.example.student.repository.StudentMarkRepository;

@Service
public class StudentMarkService {
 
	@Autowired
    private StudentMarkRepository markRepository;

    public Gd_Student_Mark saveStudentMark(Gd_Student_Mark mark) {
        // Automatically assign remark based on marks
        if (mark.getMARKS() < 40) {
            mark.setREMARK("Fail");
        } else {
            mark.setREMARK("Pass");
        }
        return markRepository.save(mark);
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

    public void deleteMarkById(int id) {
        markRepository.deleteById(id);
    }

	
}
