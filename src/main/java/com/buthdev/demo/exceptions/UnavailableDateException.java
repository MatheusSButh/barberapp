package com.buthdev.demo.exceptions;

public class UnavailableDateException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UnavailableDateException() {
		super("Unavailable date.");
	}
}
