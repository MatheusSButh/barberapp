package com.buthdev.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buthdev.demo.dtos.request.UserRequestDTO;
import com.buthdev.demo.dtos.response.UserResponseDTO;
import com.buthdev.demo.model.User;
import com.buthdev.demo.services.UserService;

@RestController
@RequestMapping(value = "users")
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping
	public ResponseEntity<List<UserResponseDTO>> findAll() {
		return ResponseEntity.ok().body(userService.findAll());
	}
	
	@PostMapping 
	public ResponseEntity<User> createUser(@RequestBody UserRequestDTO userDto) {
		userService.createUser(userDto);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}