package com.example.demo.dtos;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CategoryResponseDto {

	private Integer categoryId;
	
	private String categoryTitle;	
	
	private String categoryDescription;
}
