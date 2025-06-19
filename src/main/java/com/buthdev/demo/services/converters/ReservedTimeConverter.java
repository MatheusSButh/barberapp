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
import com.buthdev.demo.model.User;
import com.buthdev.demo.model.enums.ReservedTimeStatus;
import com.buthdev.demo.services.BarberService;
import com.buthdev.demo.services.UserService;

@Component
public class ReservedTimeConverter {

	@Autowired
	UserService userService;
	
	@Autowired
	BarberService barberService;
	
	@Autowired
	UserConverter userConverter;
	
	@Autowired
	BarberConverter barberConverter;
	
	DateTimeFormatter sdf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	
	public ReservedTime convertToReservedTime(ReservedTimeRequestDTO reservedTimeDTO, User user) {
		ReservedTime reservedTime = new ReservedTime();
		
		try {
			reservedTime.setDate(LocalDateTime.parse(reservedTimeDTO.date(), sdf));
		} 
		catch(DateTimeParseException e) {
			throw new InvalidDateException(0);
		}
		
		reservedTime.setService(reservedTimeDTO.service());
		reservedTime.setStatus(ReservedTimeStatus.VALID);
		reservedTime.setUser(user);
		reservedTime.setBarber(barberService.findById(reservedTimeDTO.barberId()));
		
		return reservedTime;
	}
	
	public List<ReservedTimeResponseDTO> convertToReservedTimeDto(List<ReservedTime> reservedTimes) {
		List<ReservedTimeResponseDTO> reservedTimeDtos = new ArrayList<>();
		
		for(ReservedTime reservedTime : reservedTimes) {
			ReservedTimeResponseDTO reservedTimeDto = new ReservedTimeResponseDTO();
			
			BeanUtils.copyProperties(reservedTime, reservedTimeDto);
			reservedTimeDto.setDate(reservedTime.getDate().format(sdf));
			reservedTimeDto.setUser(userConverter.convertToDTO(reservedTime.getUser()));
			reservedTimeDto.setBarber(barberConverter.convertToDTO(reservedTime.getBarber()));
			
			reservedTimeDtos.add(reservedTimeDto);
		}
		
		return reservedTimeDtos;
	}
}