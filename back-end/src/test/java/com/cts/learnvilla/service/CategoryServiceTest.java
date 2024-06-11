package com.cts.learnvilla.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.cts.learnvilla.dto.CategoryDto;
import com.cts.learnvilla.exception.ResourceNotFoundException;
import com.cts.learnvilla.model.Category;
import com.cts.learnvilla.repository.CategoryRepository;
import com.cts.learnvilla.service.impl.CategoryServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

	private CategoryDto categoryDto = new CategoryDto("BH", "Behavioural Courses");
	private Category category = new Category("BH", "Behavioural Courses", null);

	@Mock
	private ModelMapper modelMapper;

	@Mock
	private CategoryRepository categoryRepository;

	@InjectMocks
	private CategoryServiceImpl categoryService;

	@Test
	public void testAddCategory_ValidCategory() {
		when(categoryRepository.save(category)).thenReturn(category);
		when(modelMapper.map(categoryDto, Category.class)).thenReturn(category);
		when(modelMapper.map(category, CategoryDto.class)).thenReturn(categoryDto);
		CategoryDto resultCategoryDto = categoryService.addCategory(categoryDto);
		assertNotNull(resultCategoryDto);
		assertEquals(categoryDto.getCategoryId(), resultCategoryDto.getCategoryId());
	}

	@Test
	public void testViewAllCategories_ValidCategories() throws ResourceNotFoundException {
		Category category1 = new Category("IT", "IT & Software Courses", null);
		Category category2 = new Category("BH", "Behavioural Courses", null);
		List<Category> categories = Arrays.asList(category1, category2);
		when(categoryRepository.findAll()).thenReturn(categories);
		List<Category> resultCategories = categoryService.viewAllCategories();
		assertEquals(categories, resultCategories);
	}

	@Test
	public void testViewAllCategories_NoCategories() throws ResourceNotFoundException {
		List<Category> categories = new ArrayList<>();
		when(categoryRepository.findAll()).thenReturn(categories);
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			categoryService.viewAllCategories();
		});
		assertEquals("No categories present", exception.getMessage());
	}

	@Test
	public void testUpdateCategory_ValidCategory() throws ResourceNotFoundException {
		when(categoryRepository.findById("BH")).thenReturn(Optional.of(category));
		when(categoryRepository.save(category)).thenReturn(category);
		when(modelMapper.map(categoryDto, Category.class)).thenReturn(category);
		when(modelMapper.map(category, CategoryDto.class)).thenReturn(categoryDto);
		CategoryDto resultCategoryDto = categoryService.updateCategory(categoryDto);
		assertNotNull(resultCategoryDto);
		assertEquals(categoryDto.getCategoryId(), resultCategoryDto.getCategoryId());
	}

	@Test
	public void testUpdateCategory_InvalidCategory() throws ResourceNotFoundException {
		when(modelMapper.map(categoryDto, Category.class)).thenReturn(category);
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			categoryService.updateCategory(categoryDto);
		});
		assertEquals("No category present with the category ID : BH", exception.getMessage());
	}

	@Test
	public void testDeleteCategory_ValidCategory() throws ResourceNotFoundException {
		when(categoryRepository.findById("BH")).thenReturn(Optional.of(category));
		doNothing().when(categoryRepository).deleteById("BH");
		categoryService.deleteCategory("BH");
		verify(categoryRepository, times(1)).deleteById("BH");
	}

	@Test
	public void testDeleteCategory_InvalidCategory() throws ResourceNotFoundException {
		when(categoryRepository.findById("BH")).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			categoryService.deleteCategory("BH");
		});
		assertEquals("No category present with the category ID : BH", exception.getMessage());
	}

}
