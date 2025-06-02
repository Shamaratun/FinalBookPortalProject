package com.meme.onlinebookportal.service;

public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException() {
		super("Requested resource not found.");
	}

	public ResourceNotFoundException(String message) {
		super(message);
	}

	public ResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResourceNotFoundException(Throwable cause) {
		super(cause);
	}
}
