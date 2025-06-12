package com.buthdev.demo.exceptions;

public class InvalidDateException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidDateException(int errorIndex) {
		super("Invalid date.");
	}
}
