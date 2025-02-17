package com.example.demo.mapper;

import java.util.List;

import com.example.demo.dtos.UserDto;
import com.example.demo.dtos.UserResponse;
import com.example.demo.entities.User;

public interface UserMapper {
	public User DtoToUser(UserDto userdto);
	public UserDto usertoDto(User user);
	public List<UserDto> allUserToDto(List<User> allUser);
	
}
