package com.example.student.DTO;

import java.util.List;

public class LaptopListResponseDTO {
    private List<LaptopdetailsDTO> content;
    private long totalElements;
    private int totalPages;
    private int pageNumber;
    private int pageSize;

    // Getters and Setters

    public List<LaptopdetailsDTO> getContent() {
        return content;
    }

    public void setContent(List<LaptopdetailsDTO> content) {
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

