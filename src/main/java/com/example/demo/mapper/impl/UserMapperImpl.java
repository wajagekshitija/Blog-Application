package com.example.demo.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.dtos.UserDto;
import com.example.demo.dtos.UserResponse;
import com.example.demo.entities.User;
import com.example.demo.mapper.UserMapper;
@Component
public class UserMapperImpl implements UserMapper {

	@Override
	public User DtoToUser(UserDto userdto) {
		User user = new User();	
		user.setName(userdto.getName());
		user.setEmail(userdto.getEmail());
		user.setPassword(userdto.getPassword());
		user.setAbout(userdto.getAbout());
		return user;
	}

	@Override
	public UserDto usertoDto(User user) {
		UserDto userdto = new UserDto();
		userdto.setEmail(user.getEmail());
		userdto.setName(user.getName());
		userdto.setPassword(user.getPassword());
		userdto.setAbout(user.getAbout());
		return userdto;
	}
	
	@Override
	public List<UserDto> allUserToDto(List<User> allUser) {
		List<UserDto> userDtoList = new ArrayList<>();
		for(User users : allUser)
		{
			UserDto userDto = new UserDto();
			userDto.setName(users.getName());
			userDto.setEmail(users.getEmail());
			userDto.setPassword(users.getPassword());
			userDto.setAbout(users.getAbout());
			userDtoList.add(userDto);
		}
		return userDtoList;
	}

	

}
