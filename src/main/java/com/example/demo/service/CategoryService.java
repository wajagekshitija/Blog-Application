package com.example.demo.service;

import java.util.List;

import com.example.demo.dtos.CategoryRequestDTO;
import com.example.demo.dtos.ResponseMessage;
import com.example.demo.entities.Category;

public interface CategoryService {

	//create
	ResponseMessage createCategory(CategoryRequestDTO categoryDto);
	//update
	ResponseMessage updateCategory(CategoryRequestDTO categoryDto, Integer categoryId);
	//delete
	ResponseMessage deleteCategory(Integer categoryId);
	//get
	ResponseMessage getCategoryById(Integer categoryId);
	//getAll
	List<CategoryRequestDTO> getAllCategories();
	//pagination
	List<Category> pagination(Integer pageNumber, Integer pageSize);
	//sorting
	List<Category> sorting(String field);
	
	
}
