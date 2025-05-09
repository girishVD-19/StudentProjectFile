package com.example.student.DTO;

import java.util.List;

public class PageSortDTO<T> {
	 private List<T> content;
	    private PaginationDetails paginationDetails;

	    public PageSortDTO(List<T> content, PaginationDetails paginationDetails) {
	        this.content = content;
	        this.paginationDetails = paginationDetails;
	    }

	    // Getters and setters

	    public static class PaginationDetails {
	        private int pageNumber;
	        private int totalPages;
	        private int totalElements;

	        public PaginationDetails(int pageNumber, int totalPages, int totalElements) {
	            this.pageNumber = pageNumber;
	            this.totalPages = totalPages;
	            this.totalElements = totalElements;
	                      
	        }

			public int getPageNumber() {
				return pageNumber;
			}

			public void setPageNumber(int pageNumber) {
				this.pageNumber = pageNumber;
			}

			public int getTotalPages() {
				return totalPages;
			}

			public void setTotalPages(int totalPages) {
				this.totalPages = totalPages;
			}

			public int getTotalElements() {
				return totalElements;
			}

			public void setTotalElements(int totalElements) {
				this.totalElements = totalElements;
			}
	    }

		public List<T> getContent() {
			return content;
		}

		public void setContent(List<T> content) {
			this.content = content;
		}

		public PaginationDetails getPaginationDetails() {
			return paginationDetails;
		}

		public void setPaginationDetails(PaginationDetails paginationDetails) {
			this.paginationDetails = paginationDetails;
		}
}
