package com.example.student.Service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.student.DTO.LaptopHistoryDTO;

import com.example.student.repository.LaptopHistoryRepository;

@Service
public class LaptopHistoryService {
	@Autowired
	private LaptopHistoryRepository laptopHistoryRepository ;
	
	public LaptopHistoryService(LaptopHistoryRepository laptopHistoryRepository) {
        this.laptopHistoryRepository = laptopHistoryRepository;
    }
    public List<LaptopHistoryDTO> getLaptopHistoryByStudentId(int studentId) {
        return laptopHistoryRepository.findLaptopHistoryByStudentId(studentId);
    }
}
