package com.buthdev.demo.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buthdev.demo.dtos.request.ReservedTimeRequestDTO;
import com.buthdev.demo.dtos.response.FreeTimesResponseDTO;
import com.buthdev.demo.dtos.response.ReservedTimeResponseDTO;
import com.buthdev.demo.exceptions.InvalidDateException;
import com.buthdev.demo.exceptions.NotFoundException;
import com.buthdev.demo.exceptions.UnavailableDateException;
import com.buthdev.demo.model.ReservedTime;
import com.buthdev.demo.repositories.ReservedTimeRepository;
import com.buthdev.demo.services.converters.ReservedTimeConverter;

@Service
public class ScheduleService {

	@Autowired
	ReservedTimeRepository reservedTimeRepository;
	
	@Autowired
	UserService userService;

	@Autowired 
	private ReservedTimeConverter reservedTimeConverter;

	private final LocalTime startOfDay = LocalTime.of(10, 0);
	private final LocalTime endOfDay = LocalTime.of(18, 0);
	
	DateTimeFormatter sdf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	DateTimeFormatter sdf1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	DateTimeFormatter sdf2 = DateTimeFormatter.ofPattern("HH:mm");

	public ReservedTime createReservedTime(ReservedTimeRequestDTO reservedTimeDto) {
		ReservedTime reservedTime = reservedTimeConverter.convertToReservedTime(reservedTimeDto);
		
		LocalTime hour = reservedTime.getDate().toLocalTime();
		
		if (reservedTimeRepository.existsByDate(reservedTime.getDate()) || hour.isBefore(startOfDay) || hour.isAfter(endOfDay)) {
            throw new UnavailableDateException();
        }
		
		return reservedTimeRepository.save(reservedTime);
	}
	
	public List<ReservedTimeResponseDTO> findAll(){
		List<ReservedTime> reservedTimes = reservedTimeRepository.findAll();
		
		return reservedTimeConverter.convertToReservedTimeDto(reservedTimes);
	}
	
	public ReservedTime findById(Long id) {
		return reservedTimeRepository.findById(id).orElseThrow(() -> new NotFoundException());
	}
	
	public void deleteReservedTime(Long id) {
		findById(id);
		reservedTimeRepository.deleteById(id);
	}
	
	public List<ReservedTimeResponseDTO> findAllReservedTimeByDate(String date){
		List<ReservedTime> reservedTimes = reservedTimeRepository.findAllReservedTimeByDate(LocalDate.parse(date, sdf1));
		
		return reservedTimeConverter.convertToReservedTimeDto(reservedTimes);
	}
	
	public List<FreeTimesResponseDTO> findAllFreeTimes(String date) {
		List<FreeTimesResponseDTO> freeTimesDto = new ArrayList<>();
		
		try {
			List<ReservedTimeResponseDTO> reservedTimes = findAllReservedTimeByDate(date);
	
			freeTimesDto = convertToFreeTimesDto(verifyFreeTime(reservedTimes));
		}
		
		catch(RuntimeException e) {
			throw new InvalidDateException(0);
		}
		
		return freeTimesDto;
	}
	
	
	private List<String> verifyFreeTime(List<ReservedTimeResponseDTO> reservedTimes) {
		
		List<LocalTime> timeSlots = generateTimeSlots();
		List<String> freeTimes = new ArrayList<>();
		List<LocalTime> busyTimes = new ArrayList<>();
		
		for(ReservedTimeResponseDTO dto : reservedTimes) {
			LocalDateTime reservedTime = LocalDateTime.parse(dto.getDate(), sdf);
			
			busyTimes.add(reservedTime.toLocalTime());
		}
		
		for(LocalTime slot : timeSlots) {
			if(!busyTimes.contains(slot)) {
				freeTimes.add(slot.format(sdf2));
			}
		}
		
		return freeTimes;
	}

	private List<LocalTime> generateTimeSlots() {
		List<LocalTime> currentSlots = new ArrayList<>();
		LocalTime currentSlot = LocalTime.of(10, 0);
		
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