package com.example.demo.mapper;

import com.example.demo.dtos.CategoryRequestDTO;
import com.example.demo.entities.Category;

public interface CategoryModelMapper {
	Category DtoToCategory(CategoryRequestDTO categoryDto);
	CategoryRequestDTO categoryToDto(Category category);
}
