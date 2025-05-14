package com.example.student.DTO;

import java.util.List;


public class LaptopListResponseDTO {
    private List<LaptopdetailsDTO> content;
    private PageSortDTO pageDetails;
	public List<LaptopdetailsDTO> getContent() {
		return content;
	}
	public void setContent(List<LaptopdetailsDTO> content) {
		this.content = content;
	}
	public PageSortDTO getPageDetails() {
		return pageDetails;
	}
	
    
    


}