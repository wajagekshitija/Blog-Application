package com.example.demo.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.dtos.ResponseMessage;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		BindingResult bindingResult = ex.getBindingResult();
		Map<String, String> errorMessages = new HashMap<>();

		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			errorMessages.put(fieldError.getField(), fieldError.getDefaultMessage());
		}

		return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ResponseMessage> resourceNotFoundException(ResourceNotFoundException ex) {
//		return ResponseEntity.status(HttpStatus.NOT_FOUND)
//				.body(new ResponseMessage(HttpStatus.NOT_FOUND, ex.getMessage(), null));
		
		String message = ex.getMessage();
		ResponseMessage response = new ResponseMessage<>();
		response.setResponseMessage(message);
		response.setStatus(HttpStatus.NOT_FOUND);
		return ResponseEntity.ok(response);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<?> userNotFoundException(UserNotFoundException ex)
	{
		ResponseMessage response = new ResponseMessage<>();
		response.setResponseMessage(ex.getMessage());
		response.setStatus(HttpStatus.NOT_FOUND);
		return ResponseEntity.ok(response);
	}
	
	@ExceptionHandler(UserAlreadyRegisterException.class)
	public ResponseEntity<?> userAlreadyRegisterException(UserAlreadyRegisterException ex)
	{
		ResponseMessage response = new ResponseMessage<>();
		response.setResponseMessage(ex.getMessage());
		response.setStatus(HttpStatus.BAD_REQUEST);
		return ResponseEntity.ok(response);
	}
	
	@ExceptionHandler(CategoryNotFoundException.class)
	public ResponseEntity<?> categoryNotFoundException(CategoryNotFoundException ex)
	{
		ResponseMessage response = new ResponseMessage<>();
		response.setResponseMessage(ex.getMessage());
		response.setStatus(HttpStatus.NOT_FOUND);
		return ResponseEntity.ok(response);
	}
	
	@ExceptionHandler(FieldNotFoundException.class)
	public ResponseEntity<?> fieldNotFoundException(FieldNotFoundException ex)
	{
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ResponseMessage(HttpStatus.NOT_FOUND,ex.getMessage(),null));
	}
	
	@ExceptionHandler(PageNotFoundException.class)
	public ResponseEntity<?> PageNotFoundException(PageNotFoundException ex)
	{
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ResponseMessage(HttpStatus.NOT_FOUND,ex.getMessage(),null));
	}
}
