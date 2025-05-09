package com.example.student.DTO;

import java.util.List;

public class StudentListResponseDTO {
	 private List<StudentResponseDTO> result;
	    private PageInfoDTO pageInfo;
		public StudentListResponseDTO() {
			super();
			this.result = result;
			this.pageInfo = pageInfo;
		}
		public List<StudentResponseDTO> getResult() {
			return result;
		}
		public void setResult(List<StudentResponseDTO> result) {
			this.result = result;
		}
		public PageInfoDTO getPageInfo() {
			return pageInfo;
		}
		public void setPageInfo(PageInfoDTO pageInfo) {
			this.pageInfo = pageInfo;
		}

}
