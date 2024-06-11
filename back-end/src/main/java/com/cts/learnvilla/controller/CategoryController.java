package com.cts.learnvilla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.learnvilla.dto.CategoryDto;
import com.cts.learnvilla.model.Category;
import com.cts.learnvilla.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/learnvilla/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	/**
	 * Used to add a category
	 * 
	 * @param categoryDto
	 * @return
	 */
	@PostMapping("/add-category")
	public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto addedCategoryDto = categoryService.addCategory(categoryDto);
		return new ResponseEntity<>(addedCategoryDto, HttpStatus.CREATED);
	}
	
	/**
	 * Used to view all categories
	 * 
	 * @return
	 */
	@GetMapping("/view-all-categories")
	public ResponseEntity<List<Category>> viewAllCategories() {
		List<Category> categories = categoryService.viewAllCategories();
		return new ResponseEntity<>(categories, HttpStatus.OK);
	}

	/**
	 * Used to update category
	 * 
	 * @param categoryDto
	 * @return
	 */
	@PutMapping("/update-category")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto updatedCategoryDto = categoryService.updateCategory(categoryDto);
		return new ResponseEntity<>(updatedCategoryDto, HttpStatus.OK);
	}

	/**
	 * Used to delete category
	 * 
	 * @param categoryId
	 * @return
	 */
	@DeleteMapping("/delete-category/{categoryId}")
	public ResponseEntity<String> deleteCategory(@PathVariable String categoryId) {
		String categoryDeleted = categoryService.deleteCategory(categoryId);
		return new ResponseEntity<>("Category deleted with ID : " + categoryDeleted, HttpStatus.OK);
	}

}
