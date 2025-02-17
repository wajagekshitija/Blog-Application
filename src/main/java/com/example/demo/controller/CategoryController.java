package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.CategoryRequestDTO;
import com.example.demo.dtos.ResponseMessage;
import com.example.demo.entities.Category;
import com.example.demo.exception.CategoryNotFoundException;
import com.example.demo.exception.FieldNotFoundException;
import com.example.demo.exception.PageNotFoundException;
import com.example.demo.exception.UserAlreadyRegisterException;
import com.example.demo.service.CategoryService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/category")
@Tag(name = "Category APIs")
public class CategoryController {

	@Autowired
	private CategoryService service;

	@PostMapping("/insert")
	public ResponseMessage createCategory(@RequestBody CategoryRequestDTO categoryDto) {
		try {
			ResponseMessage response = service.createCategory(categoryDto);
			return response;
		} catch (Exception ex) {
			throw new RuntimeException("An unexpected error occurred. Please try again later.");
		}

	}

	@PutMapping("/update/{categoryId}")
	public ResponseMessage updateCategory(@RequestBody CategoryRequestDTO categoryDto,
			@PathVariable Integer categoryId) {
		try {
			ResponseMessage response = service.updateCategory(categoryDto, categoryId);
			return response;
		} catch (CategoryNotFoundException ex) {
			throw new CategoryNotFoundException("User with given email ID is already register");
		} catch (Exception ex) {
			throw new RuntimeException("An unexpected error occurred. Please try again later.");
		}
	}

	@DeleteMapping("/delete/{categoryId}")
	public ResponseMessage deleteCategory(@PathVariable Integer categoryId) {
		try {
			ResponseMessage response = service.deleteCategory(categoryId);
			return response;
		} catch (CategoryNotFoundException ex) {
			throw new CategoryNotFoundException("User with given email ID is already register");
		} catch (Exception ex) {
			throw new RuntimeException("An unexpected error occurred. Please try again later.");
		}
	}

	@GetMapping("/GetById/{categoryId}")
	public ResponseMessage getCategoryById(@PathVariable Integer categoryId) {
		try {
			ResponseMessage response = service.getCategoryById(categoryId);
			return response;
		} catch (CategoryNotFoundException ex) {
			throw new CategoryNotFoundException("User with given email ID is already register");
		} catch (Exception ex) {
			throw new RuntimeException("An unexpected error occurred. Please try again later.");
		}
	}

	@GetMapping("/GetAll")
	public List<CategoryRequestDTO> getAllCategories() {
		try {
			List<CategoryRequestDTO> categorydto = service.getAllCategories();
			return categorydto;
		} catch (Exception ex) {
			throw new RuntimeException("An unexpected error occurred. Please try again later.");
		}
	}

	@GetMapping("/pagination")
	public List<Category> pagination(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
		try {
			List<Category> result = service.pagination(pageNumber, pageSize);
			return result;
		}catch (PageNotFoundException ex) {
			throw new PageNotFoundException("User with given email ID is already register");
		} catch (Exception ex) {
			throw new RuntimeException("An unexpected error occurred. Please try again later.");
		}
	}

	@GetMapping("/sorting")
	public List<Category> sorting(@RequestParam String field) {
		try {
			List<Category> result = service.sorting(field);
			return result;
		}catch (FieldNotFoundException ex) {
			throw new FieldNotFoundException("User with given email ID is already register");
		} catch (Exception ex) {
			throw new RuntimeException("An unexpected error occurred. Please try again later.");
		}
	}

}
