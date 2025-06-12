package com.buthdev.demo.exceptions;

public class NotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NotFoundException() {
		super("Entity not found.");	
	}
}