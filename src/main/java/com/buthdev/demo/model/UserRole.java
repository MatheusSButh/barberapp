package com.buthdev.demo.model;

import lombok.Getter;

@Getter
public enum UserRole {
	
	ADMIN("admin"),
	BASIC("basic");
	
private String role;
	
	UserRole(String role){
		this.role = role;
	}
}
