package com.buthdev.demo.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buthdev.demo.dtos.request.AuthenticationRequestDTO;
import com.buthdev.demo.dtos.request.RegisterRequestDTO;
import com.buthdev.demo.dtos.response.LoginResponseDTO;
import com.buthdev.demo.model.User;
import com.buthdev.demo.repositories.UserRepository;
import com.buthdev.demo.security.services.TokenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "auth")
@Tag(name = "Authetication", description = "Endpoints to register and login. Don't require any role.")
public class AuthenticationController {

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TokenService tokenService;
	
	@Operation(summary = "Login in the system", description = "Authenticate the user.")
	@PostMapping(value = "/login")
	public ResponseEntity login(@RequestBody AuthenticationRequestDTO authDto) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(authDto.email(), authDto.password());
		var auth = authenticationManager.authenticate(usernamePassword);
		
		var token = tokenService.generateToken((User)auth.getPrincipal());
		
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}
	
	@Operation(summary = "Register in the system", description = "Registers a new user in the system.")
	@PostMapping(value = "/register")
	public ResponseEntity register(@RequestBody RegisterRequestDTO registerDto) {
		if (userRepository.findByEmail(registerDto.email()) != null) {
			return ResponseEntity.badRequest().build();
		}
		
		String encryptedPassword = new BCryptPasswordEncoder().encode(registerDto.password());
		User user = new User(registerDto.name(), registerDto.email(), encryptedPassword, registerDto.role());
		
		userRepository.save(user);
		
		return ResponseEntity.ok().build();
	}
}