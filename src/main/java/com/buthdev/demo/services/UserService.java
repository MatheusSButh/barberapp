package com.buthdev.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buthdev.demo.dtos.request.UserRequestDTO;
import com.buthdev.demo.dtos.response.UserResponseDTO;
import com.buthdev.demo.model.User;
import com.buthdev.demo.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User createUser(UserRequestDTO userDTO) {
		User user = convertToUser(userDTO);
		
		return userRepository.save(user);
	}
	
	public List<UserResponseDTO> findAll(){
		List<User> users = userRepository.findAll();
		List<UserResponseDTO> usersDto = new ArrayList<>();
		
		for(User user : users) {
			UserResponseDTO userDto = convertToDTO(user);
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
	
	
	private User convertToUser(UserRequestDTO userDTO) {
		User user = new User();
		BeanUtils.copyProperties(userDTO, user);
		
		return user;
	}
	
	private UserResponseDTO convertToDTO(User user) {
		UserResponseDTO userDto = new UserResponseDTO();
		BeanUtils.copyProperties(user, userDto);
	
		return userDto;
	}
}