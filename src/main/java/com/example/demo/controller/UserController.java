package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.ResponseMessage;
import com.example.demo.dtos.UserDto;
import com.example.demo.entities.User;
import com.example.demo.exception.FieldNotFoundException;
import com.example.demo.exception.PageNotFoundException;
import com.example.demo.exception.UserAlreadyRegisterException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/user")
@Validated
@Tag(name = "User APIs")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("/insert")
	public ResponseMessage<?> createUser(@Valid @RequestBody UserDto userdto) {
		ResponseMessage response = new ResponseMessage();
		try {
			response = userService.createUser(userdto);
			return response;
		} catch (UserAlreadyRegisterException ex) {
			throw new UserAlreadyRegisterException("User with given email ID is already register");
		} catch (Exception ex) {
			throw new RuntimeException("An unexpected error occurred. Please try again later.");
		}
	}

	@PutMapping("/update")
	public ResponseMessage updateuser(@Valid @RequestBody UserDto userDto, @RequestParam Integer userId) {
		try {
			ResponseMessage response = userService.updateuser(userDto, userId);
			return response;
		} catch (UserNotFoundException e) {
			throw new UserNotFoundException("User with given email ID is not found");
		} catch (Exception ex) {
			throw new RuntimeException("An unexpected error occurred. Please try again later.");
		}
	}

	@GetMapping("/getAllUser")
	public ResponseMessage getAllUser() {
		try {
			ResponseMessage allDtos = userService.getAllUser();
			return allDtos;
		}catch (Exception e) {
			throw new RuntimeException("An unexpected error occurred. Please try again later.");
		}
	}

	@GetMapping("/getById")
	public ResponseMessage getUserById(@Valid @RequestParam Integer userId) {
		try {
			ResponseMessage response = userService.getUserById(userId);
			return response;
		} catch (UserNotFoundException e) {
			throw new UserNotFoundException("User with given email ID is not found");
		} catch (Exception e) {
			throw new RuntimeException("An unexpected error occurred. Please try again later.");
		}
	}

	@DeleteMapping("/delete")
	public ResponseMessage deleteUser(@Valid @RequestParam Integer userId) {
		try {
			ResponseMessage response = userService.deleteUser(userId);
			return response;
		} catch (UserNotFoundException e) {
			throw new UserNotFoundException("User with given email ID is not found");
		} catch (Exception e) {
			throw new RuntimeException("An unexpected error occurred. Please try again later.");
		}
	}

	@GetMapping("/pagination")
	public List<User> pagination(@RequestParam Integer pageNumber, @RequestParam Integer pagesize) {
		try {
			List<User> user = userService.pagination(pageNumber, pagesize);
			return user;
		} catch (PageNotFoundException e) {
			throw new PageNotFoundException("Page with given page number is not found");
		} catch (Exception e) {
			throw new RuntimeException("An unexpected error occurred. Please try again later.");
		}
	}

	@GetMapping("/sorting")
	public List<User> sorting(@RequestParam String field) {
		try {
			List<User> sort = userService.sorting(field);
			return sort;
		} catch (FieldNotFoundException e) {
			throw new FieldNotFoundException("Page with given page number is not found");
		} catch (Exception e) {
			throw new RuntimeException("An unexpected error occurred. Please try again later.");
		}
	}

	@PatchMapping("/updateEntity")
	public ResponseMessage updateEntity(@RequestParam Integer userId, @RequestParam String name) {
		try {
			ResponseMessage response = userService.updateEntity(userId, name);
			return response;
		} catch (UserNotFoundException e) {
			throw new UserNotFoundException("User with given email ID is not found");
		} catch (Exception e) {
			throw new RuntimeException("An unexpected error occurred. Please try again later.");
		}
	}
}
