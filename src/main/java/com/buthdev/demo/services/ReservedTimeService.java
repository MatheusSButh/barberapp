package com.buthdev.demo.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buthdev.demo.dtos.request.ReservedTimeRequestDTO;
import com.buthdev.demo.dtos.response.ReservedTimeResponseDTO;
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
	
	public List<ReservedTimeResponseDTO> findAll(){
		List<ReservedTime> reservedTimes = reservedTimeRepository.findAll();
		List<ReservedTimeResponseDTO> reservedTimeDtos = new ArrayList<>();
		
		for(ReservedTime reservedTime : reservedTimes) {
			ReservedTimeResponseDTO reservedTimeDto = convertToReservedTimeDto(reservedTime);
			reservedTimeDtos.add(reservedTimeDto);
		}
		
		return reservedTimeDtos;
	}
	
	public ReservedTime findById(Long id) {
		return reservedTimeRepository.findById(id).orElseThrow(() -> new NullPointerException());
	}
	
	public void deleteUser(Long id) {
		findById(id);
		reservedTimeRepository.deleteById(id);
	}
	
	public List<ReservedTime> findAllReservedTimeByDate(LocalDate date){
		return reservedTimeRepository.findAllReservedTimeByDate(date);
	}
	
	
	private ReservedTime convertToReservedTime(ReservedTimeRequestDTO reservedTimeDTO) {
		ReservedTime reservedTime = new ReservedTime();
		
		reservedTime.setDate(LocalDateTime.parse(reservedTimeDTO.date(), sdf));
		reservedTime.setService(reservedTimeDTO.service());
		reservedTime.setStatus(ReservedTimeStatus.VALID);
		reservedTime.setUser(userService.findById(reservedTimeDTO.userId()));
		
		return reservedTime;
	}
	
	private ReservedTimeResponseDTO convertToReservedTimeDto(ReservedTime reservedTime) {
		ReservedTimeResponseDTO reservedTimeDto = new ReservedTimeResponseDTO();
		
		BeanUtils.copyProperties(reservedTime, reservedTimeDto);
		reservedTimeDto.setUser(userService.convertToDTO(reservedTime.getUser()));
		
		return reservedTimeDto;
	}
}