package com.example.demo.mapper.impl;

import org.springframework.stereotype.Component;

import com.example.demo.dtos.CategoryRequestDTO;
import com.example.demo.entities.Category;
import com.example.demo.mapper.CategoryModelMapper;
@Component
public class CategoryModelMapperImpl implements CategoryModelMapper{

	@Override
	public Category DtoToCategory(CategoryRequestDTO categoryDto) {
		Category category = new Category();
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		return category;
	}

	@Override
	public CategoryRequestDTO categoryToDto(Category category) {
		CategoryRequestDTO categoryDto = new CategoryRequestDTO();
		categoryDto.setCategoryDescription(category.getCategoryDescription());
		categoryDto.setCategoryTitle(category.getCategoryTitle());
		return categoryDto;
	}

}
