package com.buthdev.demo.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buthdev.demo.dtos.request.AuthenticationRequestDTO;

@RestController
@RequestMapping(value = "auth")
public class AuthenticationController {

	@Autowired
	AuthenticationManager authenticationManager;
	
	@PostMapping(value = "/login")
	public ResponseEntity login(@RequestBody AuthenticationRequestDTO authDto) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(authDto.email(), authDto.password());
		var auth = authenticationManager.authenticate(usernamePassword);
		
		return ResponseEntity.ok().build();
	}
}