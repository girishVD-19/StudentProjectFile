package com.example.student.DTO;

import com.example.student.entity.Gd_Review.ReviewStatus;

public class ReviewRequestDTO {
	
	private Integer fileSubmissionId;
    private Integer reviewerId;
    private ReviewStatus status;
    private String comment;
    
    
	public ReviewRequestDTO() {
		
	}
	public ReviewRequestDTO(Integer fileSubmissionId, Integer reviewerId, ReviewStatus status, String comment) {
		super();
		this.fileSubmissionId = fileSubmissionId;
		this.reviewerId = reviewerId;
		this.status = status;
		this.comment = comment;
	}
	public Integer getFileSubmissionId() {
		return fileSubmissionId;
	}
	public void setFileSubmissionId(Integer fileSubmissionId) {
		this.fileSubmissionId = fileSubmissionId;
	}
	public Integer getReviewerId() {
		return reviewerId;
	}
	public void setReviewerId(Integer reviewerId) {
		this.reviewerId = reviewerId;
	}
	public ReviewStatus getStatus() {
		return status;
	}
	public void setStatus(ReviewStatus status) {
		this.status = status;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
    
    

}
