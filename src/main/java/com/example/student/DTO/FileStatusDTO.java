package com.example.student.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class FileStatusDTO {
	
	private String Filename;
	private LocalDateTime creationDate;
	List<ReviewDetail> reviewdetail;
	
	
	public FileStatusDTO(String filename, LocalDateTime date, List<ReviewDetail> reviewdetail) {
		super();
		Filename = filename;
		this.creationDate = date;
		this.reviewdetail = reviewdetail;
	}


	public String getFilename() {
		return Filename;
	}


	public void setFilename(String filename) {
		Filename = filename;
	}


	public LocalDateTime getDate() {
		return creationDate;
	}


	public void setDate(LocalDateTime localDateTime) {
		this.creationDate = localDateTime;
	}


	public List<ReviewDetail> getReviewdetail() {
		return reviewdetail;
	}


	public void setReviewdetail(List<ReviewDetail> reviewdetail) {
		this.reviewdetail = reviewdetail;
	}


	public static class ReviewDetail{
		private Integer TeacharId;
		private String TeacherName;
		private String Status;
		private String comment;
		
		public ReviewDetail() {
			
		}
		
		
		public ReviewDetail(Integer teacharId, String teacherName, String status, String comment) {
			super();
			TeacharId = teacharId;
			TeacherName = teacherName;
			Status = status;
			this.comment = comment;
		}
		public Integer getTeacharId() {
			return TeacharId;
		}
		public void setTeacharId(Integer teacharId) {
			TeacharId = teacharId;
		}
		public String getTeacherName() {
			return TeacherName;
		}
		public void setTeacherName(String teacherName) {
			TeacherName = teacherName;
		}
		public String getStatus() {
			return Status;
		}
		public void setStatus(String status) {
			Status = status;
		}
		public String getComment() {
			return comment;
		}
		public void setComment(String comment) {
			this.comment = comment;
		}	
		
		
	}
	

}
