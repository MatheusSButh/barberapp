package com.buthdev.demo.services;

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
	
	public List<User> findAll(){
		return userRepository.findAll();
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
		BeanUtils.copyProperties(user, UserResponseDTO.class);
		
		
	}
}