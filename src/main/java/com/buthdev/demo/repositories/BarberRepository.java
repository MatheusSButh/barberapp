package com.buthdev.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buthdev.demo.model.Barber;

@Repository
public interface BarberRepository extends JpaRepository<Barber, Long> {
}