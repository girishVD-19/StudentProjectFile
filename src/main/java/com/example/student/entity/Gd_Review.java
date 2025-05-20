package com.example.student.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="GD_REVIEW")
public class Gd_Review {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "file_submission_id")
    private Gd_FileSubmission fileSubmission;

    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    private User reviewer;
    
    public enum ReviewStatus {
        APPROVED, REJECTED, PENDING;
    }

    @Enumerated(EnumType.STRING)
    private ReviewStatus status;

    private String comment;
    private LocalDateTime reviewedAt;
    
    public Gd_Review() {
    	
    }
    
    
	public Gd_Review(Gd_FileSubmission fileSubmission, User reviewer, ReviewStatus status, String comment,
			LocalDateTime reviewedAt) {
		super();
		this.fileSubmission = fileSubmission;
		this.reviewer = reviewer;
		this.status = status;
		this.comment = comment;
		this.reviewedAt = reviewedAt;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Gd_FileSubmission getFileSubmission() {
		return fileSubmission;
	}
	public void setFileSubmission(Gd_FileSubmission fileSubmission) {
		this.fileSubmission = fileSubmission;
	}
	public User getReviewer() {
		return reviewer;
	}
	public void setReviewer(User reviewer) {
		this.reviewer = reviewer;
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
	public LocalDateTime getReviewedAt() {
		return reviewedAt;
	}
	public void setReviewedAt(LocalDateTime reviewedAt) {
		this.reviewedAt = reviewedAt;
	}
    
    
    
    

}
