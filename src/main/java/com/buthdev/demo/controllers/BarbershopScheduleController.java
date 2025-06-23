package com.buthdev.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.buthdev.demo.dtos.response.ReservedTimeResponseDTO;
import com.buthdev.demo.services.ScheduleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "schedule")
@Tag(name = "Barber Schedule", description = "Endpoints for barber schedule management. Requires ADMIN role.")
public class BarbershopScheduleController {

	@Autowired
	ScheduleService scheduleService;
	
	@Operation(summary = "List the barber schedule", description = "Returns a list of all reserved times.")
	@GetMapping(value = "/all")
	public ResponseEntity<List<ReservedTimeResponseDTO>> findAll() {
		return ResponseEntity.ok().body(scheduleService.findAll());
	}
	
	@Operation(summary = "List the barber's schedule on a date", description = "Returns a list of all reserved times on a date.")
	@GetMapping(value = "/date")
	public ResponseEntity<List<ReservedTimeResponseDTO>> findAllReservedTimeByDate(@RequestParam String date) {
		return ResponseEntity.ok().body(scheduleService.findAllReservedTimeByDate(date));
	}
	
	@Operation(summary = "List the barber's schedule with valid reserved times", description = "Returns a list of all valid reserved times.")
	@GetMapping
	public ResponseEntity<List<ReservedTimeResponseDTO>> findAllValidReservedTime() {
		return ResponseEntity.ok().body(scheduleService.findAllValidReservedTime());
	}
	
	@Operation(summary = "Delete a reserved time", description = "Removes a reserved time from the system by their ID.")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteReservedTime(@PathVariable Long id) {
		scheduleService.deleteReservedTime(id);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}