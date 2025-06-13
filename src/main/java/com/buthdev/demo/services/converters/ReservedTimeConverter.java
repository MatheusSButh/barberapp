package com.buthdev.demo.services.converters;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.buthdev.demo.dtos.request.ReservedTimeRequestDTO;
import com.buthdev.demo.dtos.response.ReservedTimeResponseDTO;
import com.buthdev.demo.exceptions.InvalidDateException;
import com.buthdev.demo.model.ReservedTime;
import com.buthdev.demo.model.enums.ReservedTimeStatus;
import com.buthdev.demo.services.UserService;

@Component
public class ReservedTimeConverter {

	@Autowired
	UserService userService;
	
	@Autowired
	UserConverter userConverter;
	
	DateTimeFormatter sdf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	
	public ReservedTime convertToReservedTime(ReservedTimeRequestDTO reservedTimeDTO) {
		ReservedTime reservedTime = new ReservedTime();
		
		try {
			reservedTime.setDate(LocalDateTime.parse(reservedTimeDTO.date(), sdf));
		} 
		catch(DateTimeParseException e) {
			throw new InvalidDateException(0);
		}
		
		reservedTime.setService(reservedTimeDTO.service());
		reservedTime.setStatus(ReservedTimeStatus.VALID);
		reservedTime.setUser(userService.findById(reservedTimeDTO.userId()));
		
		return reservedTime;
	}
	
	public List<ReservedTimeResponseDTO> convertToReservedTimeDto(List<ReservedTime> reservedTimes) {
		List<ReservedTimeResponseDTO> reservedTimeDtos = new ArrayList<>();
		
		for(ReservedTime reservedTime : reservedTimes) {
			ReservedTimeResponseDTO reservedTimeDto = new ReservedTimeResponseDTO();
			
			BeanUtils.copyProperties(reservedTime, reservedTimeDto);
			reservedTimeDto.setDate(reservedTime.getDate().format(sdf));
			reservedTimeDto.setUser(userConverter.convertToDTO(reservedTime.getUser()));
			
			reservedTimeDtos.add(reservedTimeDto);
		}
		
		return reservedTimeDtos;
	}
}
