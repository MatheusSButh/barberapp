package com.buthdev.demo.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.buthdev.demo.model.ReservedTime;
import com.buthdev.demo.model.enums.ReservedTimeStatus;
import com.buthdev.demo.repositories.ReservedTimeRepository;

import jakarta.transaction.Transactional;

@Service
public class StatusUpdateService {

	@Autowired
	ReservedTimeRepository reservedTimeRepository;
	
	@Scheduled(fixedRate = 144000000)
	@Transactional
	public void updateStatus() {
		List<ReservedTime> reservedTimes = reservedTimeRepository.findAllReservedTimeByDateBefore(LocalDateTime.now());
		
		for(ReservedTime reservedTime : reservedTimes) {
			reservedTime.setStatus(ReservedTimeStatus.INVALID);
			reservedTimeRepository.save(reservedTime);
		}
	}
}