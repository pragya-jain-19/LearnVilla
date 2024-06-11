package com.cts.learnvilla.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.learnvilla.dto.CategoryDto;
import com.cts.learnvilla.exception.ResourceNotFoundException;
import com.cts.learnvilla.model.Category;
import com.cts.learnvilla.repository.CategoryRepository;
import com.cts.learnvilla.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Used to add a category
	 * 
	 * @param categoryDto
	 * @return
	 */
	@Override
	public CategoryDto addCategory(CategoryDto categoryDto) {
		log.info("Adding category");
		Category category = dtoToCategory(categoryDto);
		Category addedCategory = categoryRepository.save(category);
		CategoryDto addedCategoryDto = categoryToDto(addedCategory);
		log.debug("Category Added : {}", addedCategoryDto);
		log.info("Category Added successfully");
		return addedCategoryDto;
	}

	/**
	 * Used to view all categories
	 * 
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@Override
	public List<Category> viewAllCategories() throws ResourceNotFoundException {
		log.info("Viewing all the categories");
		List<Category> categories = categoryRepository.findAll();
		if (categories.size() == 0) {
			throw new ResourceNotFoundException("No categories present");
		}
		log.debug("Categories present : ");
		categories.forEach(c -> log.debug("{} {}", c.getCategoryId() ,c.getCategoryName()));
		log.info("Successfully viewed all the categories");
		return categories;
	}

	/**
	 * Used to update a category
	 * 
	 * @param categoryDto
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto) throws ResourceNotFoundException {
		log.info("Updating category");
		Category category = dtoToCategory(categoryDto);
		Optional<Category> c = categoryRepository.findById(category.getCategoryId());
		if (c.isEmpty()) {
			throw new ResourceNotFoundException(
					"No category present with the category ID : " + category.getCategoryId());
		}
		Category categoryToBeUpdated = c.get();
		categoryToBeUpdated.setCategoryName(category.getCategoryName());
		categoryToBeUpdated.setCourses(category.getCourses());
		Category updatedCategory = categoryRepository.save(categoryToBeUpdated);
		CategoryDto updatedCategoryDto = categoryToDto(updatedCategory);
		log.debug("Category Updated : {}", updatedCategoryDto);
		log.info("Category Updated successfully");
		return updatedCategoryDto;
	}

	/**
	 * Used to delete a category
	 * 
	 * @param categoryId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@Override
	public String deleteCategory(String categoryId) throws ResourceNotFoundException {
		log.info("Deleting category");
		Optional<Category> c = categoryRepository.findById(categoryId);
		if (c.isEmpty()) {
			throw new ResourceNotFoundException("No category present with the category ID : " + categoryId);
		}
		categoryRepository.deleteById(categoryId);
		log.debug("Category Deleted : {}", categoryId.toUpperCase());
		log.info("Category Deleted successfully");
		return categoryId.toUpperCase();
	}

	/**
	 * Converts CategoryDto to Category object
	 * 
	 * @param categoryDto
	 * @return
	 */
	public Category dtoToCategory(CategoryDto categoryDto) {
		return this.modelMapper.map(categoryDto, Category.class);
	}

	/**
	 * Converts Category to CategoryDto objects
	 * 
	 * @param category
	 * @return
	 */
	public CategoryDto categoryToDto(Category category) {
		return this.modelMapper.map(category, CategoryDto.class);
	}

}
