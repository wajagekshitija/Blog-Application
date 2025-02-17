package com.example.demo.exception;

public class PageNotFoundException extends RuntimeException {

	public PageNotFoundException(String message) {
		super(message);
	}

}
