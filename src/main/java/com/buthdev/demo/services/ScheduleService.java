package com.buthdev.demo.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buthdev.demo.dtos.request.ReservedTimeRequestDTO;
import com.buthdev.demo.dtos.response.FreeTimesResponseDTO;
import com.buthdev.demo.dtos.response.ReservedTimeResponseDTO;
import com.buthdev.demo.model.ReservedTime;
import com.buthdev.demo.model.enums.ReservedTimeStatus;
import com.buthdev.demo.repositories.ReservedTimeRepository;

@Service
public class ScheduleService {

	@Autowired
	ReservedTimeRepository reservedTimeRepository;
	
	@Autowired
	UserService userService;
	
	private final LocalTime startOfDay = LocalTime.of(10, 0);
	private final LocalTime endOfDay = LocalTime.of(18, 0);
	
	DateTimeFormatter sdf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	DateTimeFormatter sdf1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	DateTimeFormatter sdf2 = DateTimeFormatter.ofPattern("HH:mm");

	public ReservedTime createrReservedTime(ReservedTimeRequestDTO reservedTimeDto) {
		ReservedTime reservedTime = convertToReservedTime(reservedTimeDto);
		
		return reservedTimeRepository.save(reservedTime);
	}
	
	public List<ReservedTimeResponseDTO> findAll(){
		List<ReservedTime> reservedTimes = reservedTimeRepository.findAll();
		
		return convertToReservedTimeDto(reservedTimes);
	}
	
	public ReservedTime findById(Long id) {
		return reservedTimeRepository.findById(id).orElseThrow(() -> new NullPointerException());
	}
	
	public void deleteUser(Long id) {
		findById(id);
		reservedTimeRepository.deleteById(id);
	}
	
	public List<ReservedTimeResponseDTO> findAllReservedTimeByDate(String date){
		List<ReservedTime> reservedTimes = reservedTimeRepository.findAllReservedTimeByDate(LocalDate.parse(date, sdf1));
		
		return convertToReservedTimeDto(reservedTimes);
	}
	
	public List<FreeTimesResponseDTO> findAllFreeTimes(String date) {
		List<String> freeTimes = new ArrayList<>();
		List<LocalTime> busyTimes = new ArrayList<>();
		
		List<ReservedTimeResponseDTO> reservedTimes = findAllReservedTimeByDate(date);
		List<LocalTime> timeSlots = generateTimeSlots();
		
		for(ReservedTimeResponseDTO dto : reservedTimes) {
			LocalDateTime reservedTime = LocalDateTime.parse(dto.getDate(), sdf);
			
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
		List<LocalTime> currentSlots = new ArrayList<>();
		LocalTime currentSlot = LocalTime.of(10, 0);
		
		while(currentSlot.isBefore(endOfDay)) {
			currentSlots.add(currentSlot);
			
			currentSlot = currentSlot.plusMinutes(30);
		}
		
		return currentSlots;
	}
	
	private ReservedTime convertToReservedTime(ReservedTimeRequestDTO reservedTimeDTO) {
		ReservedTime reservedTime = new ReservedTime();
		
		reservedTime.setDate(LocalDateTime.parse(reservedTimeDTO.date(), sdf));
		reservedTime.setService(reservedTimeDTO.service());
		reservedTime.setStatus(ReservedTimeStatus.VALID);
		reservedTime.setUser(userService.findById(reservedTimeDTO.userId()));
		
		return reservedTime;
	}
	
	private List<ReservedTimeResponseDTO> convertToReservedTimeDto(List<ReservedTime> reservedTimes) {
		List<ReservedTimeResponseDTO> reservedTimeDtos = new ArrayList<>();
		
		for(ReservedTime reservedTime : reservedTimes) {
			ReservedTimeResponseDTO reservedTimeDto = new ReservedTimeResponseDTO();
			
			BeanUtils.copyProperties(reservedTime, reservedTimeDto);
			reservedTimeDto.setDate(reservedTime.getDate().format(sdf));
			reservedTimeDto.setUser(userService.convertToDTO(reservedTime.getUser()));
			
			reservedTimeDtos.add(reservedTimeDto);
		}
		
		return reservedTimeDtos;
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