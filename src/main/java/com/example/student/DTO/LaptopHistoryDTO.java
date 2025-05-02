package com.example.student.DTO;


import java.sql.Date;
import java.time.LocalDate;

public class LaptopHistoryDTO {

	 private int historyId;
	    private int laptopId;
	    private LocalDate assignedDate;
	    private LocalDate returnDate;
	    // Add studentId if needed

	    public LaptopHistoryDTO(int historyId, int laptopId, Date assignedDate, Date returnDate) {
	        this.historyId = historyId;
	        this.laptopId = laptopId;
	        this.assignedDate = (assignedDate != null) ? assignedDate.toLocalDate() : null;  // Convert SQL Date to LocalDate
	        this.returnDate = (returnDate != null) ? returnDate.toLocalDate() : null;  // Convert SQL Date to LocalDate
	    }

	    // Getters and setters
	    public int getHistoryId() {
	        return historyId;
	    }

	    public void setHistoryId(int historyId) {
	        this.historyId = historyId;
	    }

	    public int getLaptopId() {
	        return laptopId;
	    }

	    public void setLaptopId(int laptopId) {
	        this.laptopId = laptopId;
	    }

	    public LocalDate getAssignedDate() {
	        return assignedDate;
	    }

	    public void setAssignedDate(LocalDate assignedDate) {
	        this.assignedDate = assignedDate;
	    }

	    public LocalDate getReturnDate() {
	        return returnDate;
	    }

	    public void setReturnDate(LocalDate returnDate) {
	        this.returnDate = returnDate;
	    }

	    
}
