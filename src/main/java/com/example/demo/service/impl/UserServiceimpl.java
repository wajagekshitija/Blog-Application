package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.example.demo.dtos.ResponseMessage;
import com.example.demo.dtos.UserDto;
import com.example.demo.dtos.UserResponse;
import com.example.demo.entities.User;
import com.example.demo.exception.FieldNotFoundException;
import com.example.demo.exception.PageNotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserServiceimpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private UserMapper userMapper;

	// Insert
	@Override
	public ResponseMessage createUser(UserDto userdto) {
		ResponseMessage response = new ResponseMessage<>();
		try {
			User user = userRepo.getByEmail(userdto.getEmail());
			if (user != null) {
				response.setStatus(HttpStatus.CONFLICT);
				response.setResponseMessage("user Alredy existed with Email" + userdto.getEmail());
				return response;
			} else {
				user = userMapper.DtoToUser(userdto);

				User savedUser = userRepo.save(user);
				log.info("user data save in user table :" + savedUser);
				log.trace("Msg from trace");
				log.error("msg in error");
				log.debug("msg in debug");
				log.warn("msg in warn");
				UserDto dto = userMapper.usertoDto(savedUser);
				response.setStatus(HttpStatus.CREATED);
				response.setResponseMessage("user created sucess");
				response.setData(dto);
				return response;
			}
		} catch (Exception e) {
			log.error("inernal server error" + e);
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setResponseMessage("server error" + e);
			return response;
		}

	}

	// update
	@Override
	public ResponseMessage updateuser(UserDto userDto, Integer userId) {
		ResponseMessage response = new ResponseMessage<>();
		try {
			Optional<User> userOptional = userRepo.findById(userId);
			if (!userOptional.isPresent()) {
				response.setStatus(HttpStatus.NOT_FOUND);
				response.setResponseMessage("User with id " + userId + " is not present");
				return response;
			} else {
				User user = userOptional.get();
				user.setName(userDto.getName());
				user.setEmail(userDto.getEmail());
				user.setPassword(userDto.getPassword());
				user.setAbout(userDto.getAbout());
				userRepo.save(user);
				log.info("user data is updated in user table :" + user);
				userDto = userMapper.usertoDto(user);

				response.setStatus(HttpStatus.OK);
				response.setResponseMessage("user updated sucessfully");
				response.setData(userDto);
				return response;
			}
		} catch (Exception e) {
			log.error("inernal server error" + e);
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setResponseMessage("server error" + e);
			return response;
		}
	}

	// getById
	@Override
	public ResponseMessage getUserById(Integer userId) {
		ResponseMessage response = new ResponseMessage<>();
		try {
			Optional<User> userOptional = userRepo.findById(userId);
			if (!userOptional.isPresent()) {
				response.setStatus(HttpStatus.NOT_FOUND);
				response.setResponseMessage("User with id " + userId + " is not present");
				return response;
			} else {
				User user = userOptional.get();
				log.info("user with id " + userId + " is present in table");
				UserDto dto = userMapper.usertoDto(user);
				response.setStatus(HttpStatus.OK);
				response.setResponseMessage("user with id " + userId + " is present in table");
				response.setData(dto);
				return response;
			}
		} catch (Exception e) {
			log.error("inernal server error" + e);
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setResponseMessage("server error" + e);
			return response;
		}
	}

	@Override
	public ResponseMessage getAllUser() {
		ResponseMessage response = new ResponseMessage<>();
		try {
			List<User> allUser = userRepo.findAll();
			if (allUser == null) {
				response.setStatus(HttpStatus.NOT_FOUND);
				response.setResponseMessage("Data is not found");
				return response;
			} else {
				log.info("Data is present");
				log.trace("Msg from trace");
				log.error("msg in error");
				log.debug("msg in debug");
				log.warn("msg in warn");
//				List<UserDto> dto = userMapper.allUserToDto(allUser);
//				response.setStatus(HttpStatus.OK);
//				response.setResponseMessage("Data is present");
//				response.setData(dto);
//				return response;
				List<UserDto> allusers = allUser.stream().map((All) -> userMapper.usertoDto(All))
						.collect(Collectors.toList());
				response.setStatus(HttpStatus.OK);
				response.setResponseMessage("Data is present");
				response.setData(allusers);
				return response;
			}
		} catch (Exception e) {
			log.error("inernal server error" + e);
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setResponseMessage("server error" + e);
			return response;
		}

	}

	@Override
	public ResponseMessage deleteUser(Integer userId) {
		ResponseMessage response = new ResponseMessage<>();
		try {
			Optional<User> userOptional = userRepo.findById(userId);
			if (!userOptional.isPresent()) {
				response.setStatus(HttpStatus.NOT_FOUND);
				response.setResponseMessage("User with id " + userId + " is not present");
				return response;
			} else {
				log.info("Data is present");
				User user = userOptional.get();
				userRepo.deleteById(userId);
				UserDto dto = userMapper.usertoDto(user);
				response.setStatus(HttpStatus.OK);
				response.setResponseMessage("User with id " + userId + " is deleted");
				response.setData(dto);
				return response;
			}

		} catch (Exception e) {
			log.error("inernal server error" + e);
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setResponseMessage("server error" + e);
			return response;
		}
	}

	@Override
	public ResponseMessage updateEntity(Integer userId, String name) {
		ResponseMessage response = new ResponseMessage<>();
		try {
			Optional<User> userOptional = userRepo.findById(userId);
			if (!userOptional.isPresent()) {
				response.setStatus(HttpStatus.NOT_FOUND);
				response.setResponseMessage("User with id " + userId + " is not present");
				return response;
			} else {
				log.info("Data is present");
				User user = userOptional.get();
				user.setName(name);
				User updateEntity = userRepo.save(user);
				UserDto dto = userMapper.usertoDto(updateEntity);
				response.setStatus(HttpStatus.OK);
				response.setResponseMessage("Username is updated");
				response.setData(dto);
				return response;
			}
		} catch (Exception e) {
			log.error("inernal server error" + e);
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setResponseMessage("server error" + e);
			return response;
		}

	}

	@Override
	public List<User> pagination(Integer pageNumber, Integer pagesize) {
		try {
			if (pageNumber < 0 || pagesize <= 0) {
	            throw new PageNotFoundException("Page number and page size must be positive.");
	        }
			Pageable p = PageRequest.of(pageNumber, pagesize);
			Page<User> page =userRepo.findAll(p);
			
			if(page.isEmpty())
			{
				throw new PageNotFoundException("Page with page number "+pageNumber+" is not found");
			}
			return page.getContent();
		}catch (PageNotFoundException e) {
	        throw new PageNotFoundException("Page with " + pageNumber + " does not exist");
	    } catch (Exception e) {	
	        throw new RuntimeException("An error occurred while fetching users");
	    }
	}

	@Override
	public List<User> sorting(String field) {
		try {
			if (!isValidField(field)) {
				throw new FieldNotFoundException("Field " + field + " does not exist in User entity.");
			}
			return userRepo.findAll(Sort.by(Sort.Direction.ASC, field));
		} catch (FieldNotFoundException e) {
			throw new FieldNotFoundException("Invalid field name: "+field);
		} catch (Exception e) {
			throw new RuntimeException("An unexpected error occurred while sorting users.", e);
		}
	}

	private boolean isValidField(String field) {
		try {
			User.class.getDeclaredField(field);
			return true;
		} catch (NoSuchFieldException e) {
			return false;
		}
	}
}
