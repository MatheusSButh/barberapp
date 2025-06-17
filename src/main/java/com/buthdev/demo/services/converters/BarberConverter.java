package com.buthdev.demo.services.converters;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.buthdev.demo.dtos.request.BarberRequestDTO;
import com.buthdev.demo.dtos.response.BarberResponseDTO;
import com.buthdev.demo.model.Barber;

@Component
public class BarberConverter {
	
	public Barber convertToBarber(BarberRequestDTO barberDTO) {
		Barber barber = new Barber();
		BeanUtils.copyProperties(barberDTO, barber);
		
		return barber;
	}
	
	public BarberResponseDTO convertToDTO(Barber barber) {
		BarberResponseDTO barberDto = new BarberResponseDTO();
		BeanUtils.copyProperties(barber, barberDto);
	
		return barberDto;
	}
}
