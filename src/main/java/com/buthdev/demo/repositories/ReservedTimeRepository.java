package com.buthdev.demo.repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.buthdev.demo.model.ReservedTime;

@Repository
public interface ReservedTimeRepository extends JpaRepository<ReservedTime, Long> {
	
	@Query("SELECT r FROM ReservedTime r WHERE CAST(r.date AS date) = :date ORDER BY r.date ASC")
	List<ReservedTime> findAllReservedTimeByDate(@Param("date") LocalDate date);
	
	@Query("SELECT r FROM ReservedTime r WHERE r.barber.id = :barberId AND CAST(r.date AS date) = :date AND r.status = 0 ORDER BY r.date ASC")
    List<ReservedTime> findAllValidByBarberIdAndDate(@Param("barberId") Long barberId, @Param("date") LocalDate date);
	
	@Query("SELECT r FROM ReservedTime r WHERE status = 0 ORDER BY r.date ASC")
	List <ReservedTime> findAllValidReservedTime();
	
	@Query("SELECT r FROM ReservedTime r WHERE r.user.email = :email AND r.date = :date")
	Optional<ReservedTime> findReservedTimeByUserEmailAndDate(LocalDateTime date, String email);
	
	Optional<ReservedTime> findReservedTimeByBarberIdAndDate(Long id, LocalDateTime date);
	
	List<ReservedTime> findAllReservedTimeByDateBefore(LocalDateTime date);
}