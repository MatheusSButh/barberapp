package com.buthdev.demo.services;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buthdev.demo.dtos.response.FreeTimesResponseDTO;
import com.buthdev.demo.dtos.response.ReservedTimeResponseDTO;

@Service
public class FreeTimeService {
	
	@Autowired
	ReservedTimeService reservedTimeService;

	private final LocalTime startOfDay = LocalTime.of(10, 0);
	private final LocalTime endOfDay = LocalTime.of(18, 0);
	private List<LocalTime> currentSlots = new ArrayList<>();
	private LocalTime currentSlot = LocalTime.of(10, 0);
	
	DateTimeFormatter sdf1 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	DateTimeFormatter sdf2 = DateTimeFormatter.ofPattern("HH:mm");
	
	
	public List<FreeTimesResponseDTO> findAllFreeTimes(String date) {
		List<String> freeTimes = new ArrayList<>();
		List<LocalTime> busyTimes = new ArrayList<>();
		
		List<ReservedTimeResponseDTO> reservedTimes = reservedTimeService.findAllReservedTimeByDate(date);
		List<LocalTime> timeSlots = generateTimeSlots();
		
		for(ReservedTimeResponseDTO dto : reservedTimes) {
			LocalDateTime reservedTime = LocalDateTime.parse(dto.getDate(), sdf1);
			
			busyTimes.add(reservedTime.toLocalTime());
		}
		
		for(LocalTime slot : timeSlots) {
			if(!busyTimes.contains(slot)) {
				freeTimes.add(slot.format(sdf2));
			}
		}
		
		List<FreeTimesResponseDTO> freeTimesDto = convertToFreeTimesDto(freeTimes);
		
		return freeTimesDto;
	}

	
	private List<LocalTime> generateTimeSlots() {
		while(currentSlot.isBefore(endOfDay)) {
			currentSlots.add(currentSlot);
			
			currentSlot = currentSlot.plusMinutes(30);
		}
		
		return currentSlots;
	}
	
	private List<FreeTimesResponseDTO> convertToFreeTimesDto(List<String> freeTimes) {
		List<FreeTimesResponseDTO> freeTimesDto = new ArrayList<>();
		
		for(String freeTime : freeTimes) {
			FreeTimesResponseDTO freeTimeDto = new FreeTimesResponseDTO();
			
			freeTimeDto.setTime(freeTime);
			freeTimesDto.add(freeTimeDto);
		}
		
		return freeTimesDto;
	}
}