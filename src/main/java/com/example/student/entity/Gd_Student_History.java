package com.example.student.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="GD_STUDENT_HISTORY")
public class Gd_Student_History {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private int STUD_HISTORY_ID;
	
	@ManyToOne
	@JoinColumn(name="MARK_ID")
	private Gd_Student_Mark gd_student_mark;

	public Gd_Student_History(Gd_Student_Mark gd_Student_Mark) {
		super();
		this.gd_student_mark = gd_Student_Mark;
	}

	public int getSTUD_HISTORY_ID() {
		return STUD_HISTORY_ID;
	}

	public void setSTUD_HISTORY_ID(int sTUD_HISTORY_ID) {
		STUD_HISTORY_ID = sTUD_HISTORY_ID;
	}

	public Gd_Student_Mark getGd_student_mark() {
		return gd_student_mark;
	}

	public void setGd_student_mark(Gd_Student_Mark gd_Student_Mark) {
		this.gd_student_mark = gd_Student_Mark;
	}

	@Override
	public String toString() {
		return "GD_STUDENT_HISTORY [STUD_HISTORY_ID=" + STUD_HISTORY_ID + ", gd_student_mark=" + gd_student_mark + "]";
	}
	
	
}
