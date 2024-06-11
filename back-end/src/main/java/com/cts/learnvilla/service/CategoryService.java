package com.cts.learnvilla.service;

import java.util.List;

import com.cts.learnvilla.dto.CategoryDto;
import com.cts.learnvilla.exception.ResourceNotFoundException;
import com.cts.learnvilla.model.Category;

public interface CategoryService {

	/**
	 * Used to add a category
	 * 
	 * @param categoryDto
	 * @return
	 */
	public CategoryDto addCategory(CategoryDto categoryDto);

	/**
	 * Used to view all categories
	 * 
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public List<Category> viewAllCategories() throws ResourceNotFoundException;

	/**
	 * Used to update a category
	 * 
	 * @param categoryDto
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public CategoryDto updateCategory(CategoryDto categoryDto) throws ResourceNotFoundException;

	/**
	 * Used to delete a category
	 * 
	 * @param categoryId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public String deleteCategory(String categoryId) throws ResourceNotFoundException;
}
