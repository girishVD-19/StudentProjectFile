package com.example.student.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="GD_FILESUBMISSION")
public class Gd_FileSubmission {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;

	    private String fileName;
	    private String filePath;
	    private String fileType;
	    
	    @Lob
	    private byte[] fileData;

	    private boolean sentToPrincipal;

	    @ManyToOne
	    @JoinColumn(name = "uploader_id")
	    private User uploader;

	    private LocalDateTime createdAt;
	    
	    @OneToMany(mappedBy = "fileSubmission", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	    private List<Gd_Review> reviews = new ArrayList<>();
	    
	      
	    public List<Gd_Review> getReviews() {
			return reviews;
		}

		public void setReviews(List<Gd_Review> reviews) {
			this.reviews = reviews;
		}

		public Gd_FileSubmission() {
	    	
	    }

		public Gd_FileSubmission(String fileName, String filePath, byte[] fileData, boolean sentToPrincipal,
				User uploader, LocalDateTime createdAt) {
			super();
			this.fileName = fileName;
			this.filePath = filePath;
			this.fileData = fileData;
			this.sentToPrincipal = sentToPrincipal;
			this.uploader = uploader;
			this.createdAt = createdAt;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public String getFilePath() {
			return filePath;
		}

		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}

		public byte[] getFileData() {
			return fileData;
		}

		public void setFileData(byte[] fileData) {
			this.fileData = fileData;
		}

		public boolean isSentToPrincipal() {
			return sentToPrincipal;
		}

		public void setSentToPrincipal(boolean sentToPrincipal) {
			this.sentToPrincipal = sentToPrincipal;
		}

		public User getUploader() {
			return uploader;
		}

		public void setUploader(User uploader) {
			this.uploader = uploader;
		}

		public LocalDateTime getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}

		public String getFileType() {
			return fileType;
		}

		public void setFileType(String fileType) {
			this.fileType = fileType;
		}
	    
	    

		

}
