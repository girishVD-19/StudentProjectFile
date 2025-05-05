package com.example.student.DTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LaptopdetailsDTO {
	
	private Integer laptopId;
    private Integer modelNo;
    private Integer isAssigned;
    private List<StudentDTO> students = new ArrayList<>();
    private List<HistoryDTO> histories = new ArrayList<>();

    
    
    
    public LaptopdetailsDTO() {
		super();
		this.laptopId = laptopId;
		this.modelNo = modelNo;
		this.isAssigned = isAssigned;
		this.students = students;
		this.histories = histories;
	}
    
    

	public Integer getLaptopId() {
		return laptopId;
	}

	public void setLaptopId(Integer laptopId) {
		this.laptopId = laptopId;
	}

	public Integer getModelNo() {
		return modelNo;
	}

	public void setModelNo(Integer modelNo) {
		this.modelNo = modelNo;
	}

	public Integer getIsAssigned() {
		return isAssigned;
	}

	public void setIsAssigned(Integer isAssigned) {
		this.isAssigned = isAssigned;
	}

	public List<StudentDTO> getStudents() {
		return students;
	}

	public void setStudents(List<StudentDTO> students) {
		this.students = students;
	}

	public List<HistoryDTO> getHistories() {
		return histories;
	}

	public void setHistories(List<HistoryDTO> histories) {
		this.histories = histories;
	}



	public static class StudentDTO {
        private Integer studentId;
        private String studentName;
        
        
		public StudentDTO(Integer studentId, String studentName) {
			super();
			this.studentId = studentId;
			this.studentName = studentName;
		}
		public StudentDTO() {
			// TODO Auto-generated constructor stub
		}
		public Integer getStudentId() {
			return studentId;
		}
		public void setStudentId(Integer studentId) {
			this.studentId = studentId;
		}
		public String getStudentName() {
			return studentName;
		}
		public void setStudentName(String studentName) {
			this.studentName = studentName;
		}

       
    }
    
    public static class HistoryDTO {
        private Integer historyId;
      
        private LocalDate assignedDate;
        private LocalDate returnDate;
        
        
		public HistoryDTO() {
			super();
			
			this.historyId = historyId;
			this.assignedDate = assignedDate;
			this.returnDate = returnDate;
		}
		
		
		


		public Integer getHistoryId() {
			return historyId;
		}
		public void setHistoryId(Integer historyId) {
			this.historyId = historyId;
		}
		public LocalDate getAssignedDate() {
			return assignedDate;
		}
		public void setAssignedDate(LocalDate string) {
			this.assignedDate = string;
		}
		public LocalDate getReturnDate() {
			return returnDate;
		}
		public void setReturnDate(LocalDate returnDate) {
			this.returnDate = returnDate;
		}


		
    }
    

}
