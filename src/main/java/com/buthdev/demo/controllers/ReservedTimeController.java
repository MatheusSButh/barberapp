package com.buthdev.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.buthdev.demo.dtos.request.ReservedTimeRequestDTO;
import com.buthdev.demo.dtos.response.ReservedTimeResponseDTO;
import com.buthdev.demo.model.ReservedTime;
import com.buthdev.demo.services.ScheduleService;

@RestController
@RequestMapping(value = "schedule")
public class ReservedTimeController {

	@Autowired
	ScheduleService scheduleService;
	
	@PostMapping
	public ResponseEntity<ReservedTime> createReservedTime(@RequestBody ReservedTimeRequestDTO reservedTimeDTO) {
		scheduleService.createReservedTime(reservedTimeDTO);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		scheduleService.deleteReservedTime(id);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@GetMapping(value = "/date")
	public ResponseEntity<List<ReservedTimeResponseDTO>> findAllReservedTimeByDate(@RequestParam String date) {
		return ResponseEntity.ok().body(scheduleService.findAllReservedTimeByDate(date));
	}
	
	@GetMapping
	public ResponseEntity<List<ReservedTimeResponseDTO>> findAll() {
		return ResponseEntity.ok().body(scheduleService.findAll());
	}
}