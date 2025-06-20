package com.buthdev.demo.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.buthdev.demo.dtos.request.RescheduleRequestDTO;
import com.buthdev.demo.dtos.request.ReservedTimeRequestDTO;
import com.buthdev.demo.dtos.response.FreeTimesResponseDTO;
import com.buthdev.demo.dtos.response.ReservedTimeResponseDTO;
import com.buthdev.demo.exceptions.InvalidDateException;
import com.buthdev.demo.exceptions.NotFoundException;
import com.buthdev.demo.exceptions.UnavailableDateException;
import com.buthdev.demo.model.Barber;
import com.buthdev.demo.model.ReservedTime;
import com.buthdev.demo.model.User;
import com.buthdev.demo.model.enums.ReservedTimeStatus;
import com.buthdev.demo.repositories.BarberRepository;
import com.buthdev.demo.repositories.ReservedTimeRepository;
import com.buthdev.demo.services.converters.ReservedTimeConverter;

@Service
public class ScheduleService {

	@Autowired
	ReservedTimeRepository reservedTimeRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	private BarberRepository barberRepository;
	
	@Autowired
	private BarberService barberService;

	@Autowired
	private ReservedTimeConverter reservedTimeConverter;

	private final LocalTime endOfDay = LocalTime.of(17, 59);
	
	DateTimeFormatter sdf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	DateTimeFormatter sdf1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	DateTimeFormatter sdf2 = DateTimeFormatter.ofPattern("HH:mm");

	public ReservedTime createReservedTime(ReservedTimeRequestDTO reservedTimeDto, UserDetails userDetails) {
		User user = userService.findUserByEmail(userDetails.getUsername());
		ReservedTime reservedTime = reservedTimeConverter.convertToReservedTime(reservedTimeDto, user);

		verifyFreeTime(reservedTime.getBarber().getId(), reservedTime.getDate());
		
		return reservedTimeRepository.save(reservedTime);
	}
	
	public List<ReservedTimeResponseDTO> findAll(){
		List<ReservedTime> reservedTimes = reservedTimeRepository.findAll();
		
		return reservedTimeConverter.convertToReservedTimeDto(reservedTimes);
	}
	
	public List<ReservedTimeResponseDTO> findAllValidReservedTime(){
		List<ReservedTime> reservedTimes = reservedTimeRepository.findAllValidReservedTime();
		
		return reservedTimeConverter.convertToReservedTimeDto(reservedTimes);
	}
	
	public ReservedTime findById(Long id) {
		return reservedTimeRepository.findById(id).orElseThrow(() -> new NotFoundException());
	}
	
	public void deleteReservedTime(Long id) {
		findById(id);
		reservedTimeRepository.deleteById(id);
	}
	
	public void cancelReservedTimeByDate(String date, UserDetails userDetails) {
		ReservedTime reservedTime = reservedTimeRepository.findReservedTimeByUserEmailAndDate(LocalDateTime.parse(date, sdf), userDetails.getUsername())
				.orElseThrow(() -> new NotFoundException());
		
		reservedTime.setStatus(ReservedTimeStatus.INVALID);
		reservedTimeRepository.save(reservedTime);
	}
	
	public void rescheduleReservedTime(RescheduleRequestDTO rescheduleRequestDTO, UserDetails userDetails) {
		LocalDateTime currentDate = LocalDateTime.parse(rescheduleRequestDTO.currentDate(), sdf);
		LocalDateTime newDate = LocalDateTime.parse(rescheduleRequestDTO.date(), sdf);
		
		ReservedTime reservedTime = reservedTimeRepository.findReservedTimeByUserEmailAndDate(currentDate, userDetails.getUsername())
				.orElseThrow(() -> new NotFoundException());
		
		verifyFreeTime(rescheduleRequestDTO.barberId(), newDate);
		reservedTime.setBarber(barberService.findById(rescheduleRequestDTO.barberId()));
		reservedTime.setService(rescheduleRequestDTO.service());
		reservedTime.setDate(newDate);
		
		reservedTimeRepository.save(reservedTime);
	}
	
	public List<ReservedTimeResponseDTO> findAllReservedTimeByDate(String date){
		List<ReservedTime> reservedTimes = new ArrayList<>();
		
		try {
			reservedTimes = reservedTimeRepository.findAllReservedTimeByDate(LocalDate.parse(date, sdf1));
		}
		catch(RuntimeException e) {
			throw new InvalidDateException(0);
		}
		
		return reservedTimeConverter.convertToReservedTimeDto(reservedTimes);
	}
	
	public List<FreeTimesResponseDTO> findAllFreeTimes(String date) {
		List<FreeTimesResponseDTO> freeTimesDto = new ArrayList<>();
		List<Barber> barbers = barberRepository.findAll();
		
		for(Barber barber : barbers) {
			List<ReservedTime> barberReservedTimes = reservedTimeRepository.findAllValidByBarberIdAndDate(barber.getId() ,LocalDate.parse(date, sdf1));
			List<FreeTimesResponseDTO> freeTimes = getFreeTimes(barberReservedTimes, barber.getId());
			
			freeTimesDto.addAll(freeTimes);	
		}
		
		return freeTimesDto;
	}
	
	
	private List<FreeTimesResponseDTO> getFreeTimes(List<ReservedTime> reservedTimes, Long id) {
		List<LocalTime> timeSlots = generateTimeSlots();

		List<LocalTime> busyTimes = reservedTimes.stream()
				.map(rt -> rt.getDate().toLocalTime())
				.collect(Collectors.toList());
		
		List<String> freeTimes = timeSlots.stream()
				.filter(slot -> !busyTimes.contains(slot))
				.map(slot -> slot.format(sdf2))
				.collect(Collectors.toList());
			
		Barber barber = barberService.findById(id);
		
		return freeTimes.stream()
				.map(freeTime -> new FreeTimesResponseDTO(freeTime, barber.getName()))
				.collect(Collectors.toList());
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
	
	
	private void verifyFreeTime(Long barberId, LocalDateTime date) {
		Optional<ReservedTime> rt = reservedTimeRepository.findReservedTimeByBarberIdAndDate(barberId, date);
		List<LocalTime> slots = generateTimeSlots();
		
		if(!rt.isEmpty() && rt.get().getStatus().equals(ReservedTimeStatus.VALID)) { throw new UnavailableDateException(); }
		
		if(date.isBefore(LocalDateTime.now()) || !slots.contains(date.toLocalTime())) { throw new InvalidDateException(0); }
	}
}