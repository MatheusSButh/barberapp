package com.buthdev.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.buthdev.demo.dtos.response.FreeTimesResponseDTO;
import com.buthdev.demo.services.ScheduleService;

@RestController
@RequestMapping(value = "userSchedule")
public class FreeTimesController {

	@Autowired
	private ScheduleService scheduleService;
	
	@GetMapping(value = "/times")
	public ResponseEntity<List<FreeTimesResponseDTO>> findAllFreeTimes(@RequestParam String date) {
		return ResponseEntity.ok().body(scheduleService.findAllFreeTimes(date));
	}
}
