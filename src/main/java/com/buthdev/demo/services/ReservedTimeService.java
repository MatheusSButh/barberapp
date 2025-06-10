package com.buthdev.demo.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buthdev.demo.dtos.request.ReservedTimeRequestDTO;
import com.buthdev.demo.model.ReservedTime;
import com.buthdev.demo.model.enums.ReservedTimeStatus;
import com.buthdev.demo.repositories.ReservedTimeRepository;

@Service
public class ReservedTimeService {
	
	@Autowired
	ReservedTimeRepository reservedTimeRepository;
	
	@Autowired
	UserService userService;
	
	DateTimeFormatter sdf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	
	public ReservedTime createrReservedTime(ReservedTimeRequestDTO reservedTimeDto) {
		ReservedTime reservedTime = convertToReservedTime(reservedTimeDto);
		
		return reservedTimeRepository.save(reservedTime);
	}
	
	
	private ReservedTime convertToReservedTime(ReservedTimeRequestDTO reservedTimeDTO) {
		ReservedTime reservedTime = new ReservedTime();
		
		reservedTime.setDate(LocalDateTime.parse(reservedTimeDTO.date(), sdf));
		reservedTime.setService(reservedTimeDTO.service());
		reservedTime.setStatus(ReservedTimeStatus.VALID);
		reservedTime.setUser(userService.findById(reservedTimeDTO.userId()));
		
		return reservedTime;
	}
}