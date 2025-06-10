package com.buthdev.demo.mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.mapstruct.Mapper;

import com.buthdev.demo.dtos.request.ReservedTimeRequestDTO;
import com.buthdev.demo.dtos.response.ReservedTimeResponseDTO;
import com.buthdev.demo.model.ReservedTime;

@Mapper
public interface ReservedTimeMapper {

	 ReservedTime toEntity(ReservedTimeRequestDTO dto);
	 
	 ReservedTimeResponseDTO toDto(ReservedTime entity);
	
	default LocalDateTime stringToLocalDateTime(String dateString) {
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		 
		 return LocalDateTime.parse(dateString, dtf);
	 }
}