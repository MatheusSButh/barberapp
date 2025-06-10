package com.buthdev.demo.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buthdev.demo.model.ReservedTime;

@Repository
public interface ReservedTimeRepository extends JpaRepository<ReservedTime, Long> {
	
	public List<ReservedTime> findAllReservedTimeByDate(LocalDate date);
}