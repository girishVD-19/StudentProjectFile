package com.example.student.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="GD_STUDENT_MARK")
public class Gd_Student_Mark {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private int MARK_ID;
	
	@ManyToOne
	@JoinColumn(name="STUDENT_ID")
	private Gd_Student gd_student;

	@Column
	private int MARKS;
	
	@Column
	private String REMARK;
	
	@JsonIgnore
	@OneToMany(mappedBy="gd_student_mark" ,cascade=CascadeType.ALL)
	private List<Gd_Student_History>gd_student_history;
	
	@ManyToOne
	@JoinColumn(name="SR_NO")
	private Gd_Subject_Mapping gd_subject_mapping;
	
	public Gd_Student_Mark() {
		
	}

	public Gd_Student_Mark(Gd_Student gd_Student, int mARKS, String rEMARK,
			List<Gd_Student_History> gd_Student_History) {
		super();
		this.gd_student = gd_Student;
		
		MARKS = mARKS;
		REMARK = rEMARK;
		this.gd_student_history = gd_Student_History;
	}

	public int getMARK_ID() {
		return MARK_ID;
	}

	public void setMARK_ID(int mARK_ID) {
		MARK_ID = mARK_ID;
	}

	public Gd_Student getGd_student() {
		return gd_student;
	}

	public void setGd_student(Gd_Student gd_Student) {
		this.gd_student = gd_Student;
	}


	public int getMARKS() {
		return MARKS;
	}

	public void setMARKS(int mARKS) {
		MARKS = mARKS;
	}

	public String getREMARK() {
		return REMARK;
	}

	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}

	public List<Gd_Student_History> getGd_student_mapping() {
		return gd_student_history;
	}

	public void setGd_student_mapping(List<Gd_Student_History> gd_Student_History) {
		this.gd_student_history = gd_Student_History;
	}

	@Override
	public String toString() {
		return "GD_STUDENT_MARK [MARK_ID=" + MARK_ID + ", gd_student=" + gd_student +
				 ", MARKS=" + MARKS + ", REMARK=" + REMARK + ", gd_student_history=" + gd_student_history + "]";
	}

	public Gd_Student_Mark(int mARK_ID, Gd_Student gd_student, int mARKS, String rEMARK,
			List<Gd_Student_History> gd_student_history, Gd_Subject_Mapping gd_subject_mapping) {
		super();
		MARK_ID = mARK_ID;
		this.gd_student = gd_student;
		MARKS = mARKS;
		REMARK = rEMARK;
		this.gd_student_history = gd_student_history;
		this.gd_subject_mapping = gd_subject_mapping;
	}

	public Gd_Subject_Mapping getGd_subject_mapping() {
		return gd_subject_mapping;
	}

	public void setGd_subject_mapping(Gd_Subject_Mapping gd_subject_mapping) {
		this.gd_subject_mapping = gd_subject_mapping;
	}
	
	
	
	
	
	
	
}
