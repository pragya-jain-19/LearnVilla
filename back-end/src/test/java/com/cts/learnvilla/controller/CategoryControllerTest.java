package com.cts.learnvilla.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.cts.learnvilla.dto.CategoryDto;
import com.cts.learnvilla.model.Category;
import com.cts.learnvilla.service.CategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc(addFilters = false)
public class CategoryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CategoryService categoryService;

	private static ObjectMapper objectMapper;

	public String mapToJson(Object object) throws JsonProcessingException {
		return objectMapper.writeValueAsString(object);
	}

	private CategoryDto CAT_DTO_1 = new CategoryDto("BH", "Behavioural Courses");
	private Category CAT_1 = new Category("BH", "Behavioural Courses", null);

	@BeforeAll
	public static void setUp() {
		objectMapper = new ObjectMapper();
	}

	@Test
	void testCreateCategory() throws Exception {
		when(categoryService.addCategory(CAT_DTO_1)).thenReturn(CAT_DTO_1);

		mockMvc.perform(post("/learnvilla/api/categories/add-category").contentType(MediaType.APPLICATION_JSON)
				.content(mapToJson(CAT_DTO_1)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.categoryId").value("BH"));
	}

	@Test
	void testFindCategories() throws Exception {
		when(categoryService.viewAllCategories()).thenReturn(Arrays.asList(CAT_1));
		mockMvc.perform(get("/learnvilla/api/categories/view-all-categories").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(1));
	}

	@Test
	void testUpdateCategory() throws Exception {
		when(categoryService.updateCategory(CAT_DTO_1)).thenReturn(CAT_DTO_1);
		mockMvc.perform(put("/learnvilla/api/categories/update-category").contentType(MediaType.APPLICATION_JSON)
				.content(mapToJson(CAT_DTO_1)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.categoryId").value("BH"));
	}

	@Test
	void testDeleteCategory() throws Exception {
		when(categoryService.deleteCategory("BH")).thenReturn("BH");
		mockMvc.perform(delete("/learnvilla/api/categories/delete-category/{categoryId}", "BH").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$").value("Category deleted with ID : BH"));

	}

}