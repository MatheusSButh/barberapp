package com.buthdev.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buthdev.demo.dtos.request.UserRequestDTO;
import com.buthdev.demo.dtos.response.UserResponseDTO;
import com.buthdev.demo.model.User;
import com.buthdev.demo.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "users")
@Tag(name = "Users", description = "Endpoints for users management. Requires ADMIN role.")
public class UserController {

	@Autowired
	UserService userService;
	
	@Operation(summary = "List all users", description = "Returns a list of all users.")
	@GetMapping
	public ResponseEntity<List<UserResponseDTO>> findAll() {
		return ResponseEntity.ok().body(userService.findAll());
	}
	
	@Operation(summary = "Update an existing user", description = "Updates a specific user data by their ID.")
	@PutMapping(value = "/{id}")
	public ResponseEntity<User> updateUser (@RequestBody UserRequestDTO userDto, @PathVariable Long id) {
		userService.updateUser(userDto, id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@Operation(summary = "Delete a user", description = "Removes a user from the system by their ID.")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}