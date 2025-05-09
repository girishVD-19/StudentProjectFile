package com.example.student.DTO;

public class PageInfoDTO {
	 private Long totalRecords;      // Total number of records
	    private Integer currentPageNo;  // Current page number
	    private Integer pageSize;
	    
	    
		public PageInfoDTO() {
			super();
			
		}
		public Long getTotalRecords() {
			return totalRecords;
		}
		public void setTotalRecords(Long totalRecords) {
			this.totalRecords = totalRecords;
		}
		public Integer getCurrentPageNo() {
			return currentPageNo;
		}
		public void setCurrentPageNo(Integer currentPageNo) {
			this.currentPageNo = currentPageNo;
		}
		public Integer getPageSize() {
			return pageSize;
		}
		public void setPageSize(Integer pageSize) {
			this.pageSize = pageSize;
		}      
	    
	    

}
