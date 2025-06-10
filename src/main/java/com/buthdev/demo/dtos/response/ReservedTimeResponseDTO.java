package com.buthdev.demo.dtos.response;

import java.time.LocalDateTime;

import com.buthdev.demo.model.User;
import com.buthdev.demo.model.enums.ReservedTimeStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReservedTimeResponseDTO {

	private LocalDateTime date;
	private String service;
	private ReservedTimeStatus status;
	private User user;
}