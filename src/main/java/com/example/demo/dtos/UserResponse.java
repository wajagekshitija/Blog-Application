package com.example.demo.dtos;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserResponse {
	private int id;
	@Column(name = "user_name", nullable = false, length = 100)
	private String name;
	private String email;
	private String password;
	private String about;
}
