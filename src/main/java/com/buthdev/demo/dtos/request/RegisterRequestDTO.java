package com.buthdev.demo.dtos.request;

import com.buthdev.demo.model.UserRole;

public record RegisterRequestDTO(String name, String email, String password, UserRole role) {

}
