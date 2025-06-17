package com.buthdev.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buthdev.demo.dtos.request.BarberRequestDTO;
import com.buthdev.demo.dtos.response.BarberResponseDTO;
import com.buthdev.demo.model.Barber;
import com.buthdev.demo.services.BarberService;

@RestController
@RequestMapping(value = "barbers")
public class BarberController {

	@Autowired
	BarberService barberService;
	
	@GetMapping
	public ResponseEntity<List<BarberResponseDTO>> findAll() {
		return ResponseEntity.ok().body(barberService.findAll());
	}
	
	@PostMapping 
	public ResponseEntity<Barber> createBarber(@RequestBody BarberRequestDTO barberDto) {
		barberService.createBarber(barberDto);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Barber> updateBarber (@RequestBody BarberRequestDTO barberDto, @PathVariable Long id) {
		barberService.updateBarber(barberDto, id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteBarber(@PathVariable Long id) {
		barberService.deleteBarber(id);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}