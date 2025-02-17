package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.dtos.ResponseMessage;
import com.example.demo.dtos.UserDto;
import com.example.demo.entities.User;

public interface UserService {
	
	ResponseMessage createUser(UserDto user);
	ResponseMessage updateuser(UserDto user,Integer UserId);
	ResponseMessage getUserById(Integer userId);
	ResponseMessage getAllUser();
	ResponseMessage deleteUser(Integer userId); 
	ResponseMessage updateEntity(Integer userId,String name);
	List<User> pagination(Integer pageNumber, Integer pagesize);
	List<User> sorting(String field);

}
