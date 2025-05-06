package com.example.student.DTO;

import java.util.List;

public class ClassResponseDTO {
	 private List<ClassDetailsDTO> content;
	    private long totalElements;
	    private int totalPages;
	    private int pageNumber;
	    private int pageSize;

	    // Getters and Setters

	    public List<ClassDetailsDTO> getContent() {
	        return content;
	    }

	    public void setContent(List<ClassDetailsDTO> content) {
	        this.content = content;
	    }

	    public long getTotalElements() {
	        return totalElements;
	    }

	    public void setTotalElements(long totalElements) {
	        this.totalElements = totalElements;
	    }

	    public int getTotalPages() {
	        return totalPages;
	    }

	    public void setTotalPages(int totalPages) {
	        this.totalPages = totalPages;
	    }

	    public int getPageNumber() {
	        return pageNumber;
	    }

	    public void setPageNumber(int pageNumber) {
	        this.pageNumber = pageNumber;
	    }

	    public int getPageSize() {
	        return pageSize;
	    }

	    public void setPageSize(int pageSize) {
	        this.pageSize = pageSize;
	    }

}
