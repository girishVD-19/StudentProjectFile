package com.example.student.DTO;

public class SubjectMappingDTO {
	private int srno;
    private ClassDetailsDTO gdclass;
    
    private SubjectDTO Subject;
    
    

	public SubjectMappingDTO(int srno, ClassDetailsDTO gdclass, SubjectDTO subject) {
		super();
		this.srno = srno;
		this.gdclass = gdclass;
		Subject = subject;
	}

	public int getSrno() {
		return srno;
	}

	public void setSrno(int srno) {
		this.srno = srno;
	}

	public ClassDetailsDTO getGdclass() {
		return gdclass;
	}

	public void setGdclass(ClassDetailsDTO gdclass) {
		this.gdclass = gdclass;
	}

	public SubjectDTO getSubject() {
		return Subject;
	}

	public void setSubject(SubjectDTO subject) {
		Subject = subject;
	}

	
    
    
    
    

}
