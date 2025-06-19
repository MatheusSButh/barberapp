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

import com.buthdev.demo.dtos.request.ReservedTimeRequestDTO;
import com.buthdev.demo.dtos.response.FreeTimesResponseDTO;
import com.buthdev.demo.model.ReservedTime;
import com.buthdev.demo.services.ScheduleService;

@RestController
@RequestMapping(value = "userSchedule")
public class UserScheduleController {

	@Autowired
	private ScheduleService scheduleService;
	
	@PostMapping
	public ResponseEntity<ReservedTime> createReservedTime(@RequestBody ReservedTimeRequestDTO reservedTimeDTO, @AuthenticationPrincipal UserDetails userDetails) {
		scheduleService.createReservedTime(reservedTimeDTO, userDetails);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping(value = "/times")
	public ResponseEntity<List<FreeTimesResponseDTO>> findAllFreeTimes(@RequestParam String date) {
		return ResponseEntity.ok().body(scheduleService.findAllFreeTimes(date));
	}
	
	@PutMapping(value = "/cancel")
	public ResponseEntity<ReservedTime> cancelReservedTime(@RequestParam String date, @AuthenticationPrincipal UserDetails userDetails) {
		scheduleService.cancelReservedTimeByDate(date, userDetails);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}