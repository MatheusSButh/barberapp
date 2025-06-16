package com.buthdev.demo.repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.buthdev.demo.model.ReservedTime;

@Repository
public interface ReservedTimeRepository extends JpaRepository<ReservedTime, Long> {
	
	@Query("SELECT r FROM ReservedTime r WHERE CAST(r.date AS date) = :date")
	public List<ReservedTime> findAllReservedTimeByDate(@Param("date") LocalDate date);
	
	public List<ReservedTime> findAllReservedTimeByDateBefore(LocalDateTime date);
	
	@Query("SELECT r FROM ReservedTime r WHERE status = 0")
	public List <ReservedTime> findAllValidReservedTime();
	
	boolean existsByDate(LocalDateTime date);
}