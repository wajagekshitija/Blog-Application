package com.example.demo.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryRequestDTO {

	@NotEmpty(message = "Title is required")
	@Size(min = 10, max = 20, message = "Title contain atleast 10 and atmost 20 letters")
	private String categoryTitle;
	@Size(max = 1000, message = "Description contain 1000 lettes")
	private String categoryDescription;
}
