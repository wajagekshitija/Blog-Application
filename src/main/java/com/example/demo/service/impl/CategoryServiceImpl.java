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
import com.example.demo.dtos.CategoryRequestDTO;
import com.example.demo.dtos.ResponseMessage;
import com.example.demo.entities.Category;
import com.example.demo.entities.User;
import com.example.demo.exception.CategoryNotFoundException;
import com.example.demo.exception.FieldNotFoundException;
import com.example.demo.exception.PageNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.mapper.CategoryModelMapper;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository repository;

	@Autowired
	private CategoryModelMapper categoryMapper;

	@Override
	public ResponseMessage createCategory(CategoryRequestDTO categoryDto) {
		ResponseMessage response = new ResponseMessage<>();
		try {
			Category category = categoryMapper.DtoToCategory(categoryDto);
			Category savedCategory = repository.save(category);
			log.info("Category created successfully");
			CategoryRequestDTO dto = categoryMapper.categoryToDto(savedCategory);
			response.setStatus(HttpStatus.CREATED);
			response.setResponseMessage("Category Created Successfully");
			response.setData(dto);
			return response;
		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setResponseMessage("server error" + e);
			return response;
		}

	}

	@Override
	public ResponseMessage updateCategory(CategoryRequestDTO categoryDto, Integer categoryId) {
		ResponseMessage response = new ResponseMessage<>();
		try {
			Category category = repository.findById(categoryId).orElseThrow(
					() -> new CategoryNotFoundException("Category with categoryId " + categoryId + " is not present"));
			log.info("category is present");
			category.setCategoryTitle(categoryDto.getCategoryTitle());
			category.setCategoryDescription(categoryDto.getCategoryDescription());
			repository.save(category);
			log.info("Category details updated in category table " + category);
			CategoryRequestDTO dto = categoryMapper.categoryToDto(category);
			response.setStatus(HttpStatus.OK);
			response.setResponseMessage("Category Updated Successfully");
			response.setData(dto);
			return response;
		} catch (CategoryNotFoundException e) {
			response.setStatus(HttpStatus.NOT_FOUND);
			response.setResponseMessage("Category with categoryId " + categoryId + " is not found");
			return response;
		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setResponseMessage("server error" + e);
			return response;
		}
	}

	@Override
	public ResponseMessage deleteCategory(Integer categoryId) {
		ResponseMessage response = new ResponseMessage<>();
		try {
			Optional<Category> categoryOptional = repository.findById(categoryId);
			if (!categoryOptional.isPresent()) {
				throw new CategoryNotFoundException("Category with categoryId " + categoryId + " is not present");
			} else {
				log.info("category is present");
				Category category = categoryOptional.get();
				repository.deleteById(categoryId);
				log.info("Category is deleted from category table");
				CategoryRequestDTO dto = categoryMapper.categoryToDto(category);

				response.setStatus(HttpStatus.OK);
				response.setResponseMessage("category deleted successfully");
				response.setData(dto);
				return response;
			}
		} catch (CategoryNotFoundException e) {
			response.setStatus(HttpStatus.NOT_FOUND);
			response.setResponseMessage("Category with categoryId " + categoryId + " is not found");
			return response;
		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setResponseMessage("server error" + e);
			return response;
		}

	}

	@Override
	public ResponseMessage getCategoryById(Integer categoryId) {
		ResponseMessage response = new ResponseMessage<>();
		try {
			Optional<Category> categoryOptional = repository.findById(categoryId);
			if (!categoryOptional.isPresent()) {
				throw new CategoryNotFoundException("Category with categoryId " + categoryId + " is not present");
			} else {
				log.info("category is present");
				Category category = categoryOptional.get();
				log.info("category with categoryId " + categoryId + " fetched successfully");
				CategoryRequestDTO dto = categoryMapper.categoryToDto(category);
				response.setStatus(HttpStatus.OK);
				response.setResponseMessage("category with categoryId " + categoryId + " fetched successfully");
				response.setData(dto);
				return response;

			}
		} catch (CategoryNotFoundException e) {
			response.setStatus(HttpStatus.NOT_FOUND);
			response.setResponseMessage("Category with categoryId " + categoryId + " is not found");
			return response;
		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setResponseMessage("server error" + e);
			return response;
		}
	}

	@Override
	public List<CategoryRequestDTO> getAllCategories() {
		List<Category> categories = repository.findAll();
		List<CategoryRequestDTO> categoryDto = categories.stream().map((cat) -> categoryMapper.categoryToDto(cat))
				.collect(Collectors.toList());
		return categoryDto;
	}

	@Override
	public List<Category> pagination(Integer pageNumber, Integer pageSize) {
		ResponseMessage response = new ResponseMessage<>();
		try {
			if (pageNumber < 0 || pageSize <= 0) {
				throw new PageNotFoundException("pageNumber and pageSize must be positive");
			}
			Pageable p = PageRequest.of(pageNumber, pageSize);
			Page<Category> page = repository.findAll(p);
			if (page.isEmpty()) {
				throw new PageNotFoundException("Page with " + pageNumber + " is not found");
			}
			return page.getContent();
		} catch (PageNotFoundException e) {
			throw new PageNotFoundException("Page with " + pageNumber + " is not found");
		} catch (Exception e) {
			throw new RuntimeException("An error occured while fetching categories");
		}

	}

	@Override
	public List<Category> sorting(String field) {
		try {
			if (!isValidField(field)) {
				throw new FieldNotFoundException("Invalid field name: " + field);
			}
			List<Category> sortCategories = repository.findAll(Sort.by(Sort.Direction.ASC, field));
			return sortCategories;
		} catch (FieldNotFoundException e) {
			throw new FieldNotFoundException("Invalid field name: " + field);
		} catch (Exception e) {
			throw new RuntimeException("An unexpected error occurred while sorting users.", e);
		}

	}

	public boolean isValidField(String field) {
		try {
			Category.class.getDeclaredField(field);
			return true;
		} catch (NoSuchFieldException e) {
			return false;
		}
	}

}
