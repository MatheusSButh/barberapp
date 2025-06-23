package com.buthdev.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.buthdev.demo.dtos.request.RescheduleRequestDTO;
import com.buthdev.demo.dtos.request.ReservedTimeRequestDTO;
import com.buthdev.demo.dtos.response.FreeTimesResponseDTO;
import com.buthdev.demo.model.ReservedTime;
import com.buthdev.demo.services.ScheduleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "userSchedule")
@Tag(name = "User Schedule", description = "Endpoints for user schedule management. Requires BASIC role.")
public class UserScheduleController {

	@Autowired
	private ScheduleService scheduleService;
	
	@Operation(summary = "Create a reserved time", description = "Registers a new reserved time in the system.")
	@PostMapping
	public ResponseEntity<ReservedTime> createReservedTime(@RequestBody ReservedTimeRequestDTO reservedTimeDTO, @AuthenticationPrincipal UserDetails userDetails) {
		scheduleService.createReservedTime(reservedTimeDTO, userDetails);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@Operation(summary = "List all free times on a date", description = "Returns a list of all free times on a date.")
	@GetMapping(value = "/freeTimes")
	public ResponseEntity<List<FreeTimesResponseDTO>> findAllFreeTimes(@RequestParam String date) {
		return ResponseEntity.ok().body(scheduleService.findAllFreeTimes(date));
	}
	
	@Operation(summary = "Cancel a reserved time", description = "Set a reserved time status to INVALID.")
	@PutMapping(value = "/cancel")
	public ResponseEntity<ReservedTime> cancelReservedTime(@RequestParam String date, @AuthenticationPrincipal UserDetails userDetails) {
		scheduleService.cancelReservedTimeByDate(date, userDetails);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@Operation(summary = "Reschedule a reserved time", description = "Reschedule a reserved time to another possible date.")
	@PutMapping(value = "/reschedule")
	public ResponseEntity<ReservedTime> rescheduleReservedTime(@RequestBody RescheduleRequestDTO rescheduleRequestDTO, @AuthenticationPrincipal UserDetails userDetails) {
		scheduleService.rescheduleReservedTime(rescheduleRequestDTO, userDetails);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}