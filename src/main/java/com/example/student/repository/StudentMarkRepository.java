package com.example.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.student.entity.Gd_Student_Mark;

public interface StudentMarkRepository extends JpaRepository<Gd_Student_Mark,Integer> {
	
	@Query("SELECT m FROM Gd_Student_Mark m WHERE m.gd_student.STUDENT_ID = :studentId")
    List<Gd_Student_Mark> findMarksByStudentId(@Param("studentId") int studentId);
    
    
    @Query(value = "SELECT s.NAME, sub.subject_name, c.class_name, m.marks, m.remark " +
            "FROM GD_STUDENT_MARK m " +
            "JOIN GD_STUDENT s ON m.STUDENT_ID = s.STUDENT_ID " +
            "JOIN GD_SUBJECT_MAPPTING sm ON m.SR_NO = sm.SR_NO " +
            "JOIN GD_SUBJECT sub ON sm.SUBJECT_ID = sub.SUBJECT_ID " +
            "JOIN GD_CLASS c ON sm.CLASS_ID = c.CLASS_ID " +
            "WHERE s.STUDENT_ID = :studentId AND sm.SR_NO = :srNo", nativeQuery = true)
List<Object[]> findStudentMarksNative(@Param("studentId") int studentId, @Param("srNo") int srNo);



    @Query(value = "SELECT mark.* FROM GD_STUDENT_MARK mark " +
                   "JOIN GD_STUDENT student ON mark.STUDENT_ID = student.STUDENT_ID " +
                   "JOIN GD_SUBJECT_MAPPTING subjectMapping ON mark.SR_NO = subjectMapping.SR_NO " +
                   "JOIN GD_SUBJECT subject ON subjectMapping.SUBJECT_ID = subject.SUBJECT_ID " +
                   "WHERE student.STUDENT_ID = :studentId " +
                   "AND subjectMapping.CLASS_ID = :classId", nativeQuery = true)
    List<Gd_Student_Mark> findMarksByStudentIdAndClassId(@Param("studentId") int studentId, 
                                                          @Param("classId") int classId);

}
