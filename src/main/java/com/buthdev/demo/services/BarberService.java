package com.buthdev.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buthdev.demo.dtos.request.BarberRequestDTO;
import com.buthdev.demo.dtos.response.BarberResponseDTO;
import com.buthdev.demo.exceptions.NotFoundException;
import com.buthdev.demo.model.Barber;
import com.buthdev.demo.repositories.BarberRepository;
import com.buthdev.demo.services.converters.BarberConverter;

@Service
public class BarberService {

	@Autowired
	private BarberRepository barberRepository;
	
	@Autowired
	private BarberConverter barberConverter;
	
	public Barber createBarber(BarberRequestDTO barberDto) {
		Barber barber = barberConverter.convertToBarber(barberDto);
		
		return barberRepository.save(barber);
	}
	
	public Barber updateBarber(BarberRequestDTO barberDto, Long id) {
		Barber barber = findById(id);
		barberConverter.convertToBarber(barberDto);
		return barberRepository.save(barber);
	}
	
	public List<BarberResponseDTO> findAll(){
		List<Barber> barbers = barberRepository.findAll();
		List<BarberResponseDTO> barbersDto = new ArrayList<>();
		
		for(Barber barber : barbers) {
			BarberResponseDTO barberDto = barberConverter.convertToDTO(barber);
			barbersDto.add(barberDto);
		}
		
		return barbersDto;
	}
	
	public Barber findById(Long id) {
		return barberRepository.findById(id).orElseThrow(() -> new NotFoundException());
	}
	
	public void deleteBarber(Long id) {
		findById(id);
		barberRepository.deleteById(id);
	}
}