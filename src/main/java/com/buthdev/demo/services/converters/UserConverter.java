package com.buthdev.demo.services.converters;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.buthdev.demo.dtos.request.UserRequestDTO;
import com.buthdev.demo.dtos.response.UserResponseDTO;
import com.buthdev.demo.model.User;

@Component
public class UserConverter {
	
	public User convertToUser(UserRequestDTO userDTO) {
		User user = new User();
		BeanUtils.copyProperties(userDTO, user);
		
		return user;
	}
	
	public UserResponseDTO convertToDTO(User user) {
		UserResponseDTO userDto = new UserResponseDTO();
		BeanUtils.copyProperties(user, userDto);
	
		return userDto;
	}
}
