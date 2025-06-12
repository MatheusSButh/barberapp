package com.buthdev.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buthdev.demo.dtos.request.UserRequestDTO;
import com.buthdev.demo.dtos.response.UserResponseDTO;
import com.buthdev.demo.model.User;
import com.buthdev.demo.repositories.UserRepository;
import com.buthdev.demo.services.converters.UserConverter;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserConverter userConverter;
	
	public User createUser(UserRequestDTO userDTO) {
		User user = userConverter.convertToUser(userDTO);
		
		return userRepository.save(user);
	}
	
	public List<UserResponseDTO> findAll(){
		List<User> users = userRepository.findAll();
		List<UserResponseDTO> usersDto = new ArrayList<>();
		
		for(User user : users) {
			UserResponseDTO userDto = userConverter.convertToDTO(user);
			usersDto.add(userDto);
		}
		
		return usersDto;
	}
	
	public User findById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new NullPointerException());
	}
	
	public void deleteUser(Long id) {
		findById(id);
		userRepository.deleteById(id);
	}
}