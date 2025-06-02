package com.meme.onlinebookportal.service;

public class DuplicateResourceException extends RuntimeException {

	public DuplicateResourceException() {
		super("Duplicate resource found.");
	}

	public DuplicateResourceException(String message) {
		super(message);
	}

	public DuplicateResourceException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateResourceException(Throwable cause) {
		super(cause);
	}
}
