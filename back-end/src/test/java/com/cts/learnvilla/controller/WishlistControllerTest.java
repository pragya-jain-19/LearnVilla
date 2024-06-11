package com.cts.learnvilla.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.cts.learnvilla.dto.WishlistDto;
import com.cts.learnvilla.model.Category;
import com.cts.learnvilla.model.Course;
import com.cts.learnvilla.model.Student;
import com.cts.learnvilla.model.Wishlist;
import com.cts.learnvilla.service.WishlistService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc(addFilters = false)
public class WishlistControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private WishlistService wishlistService;

	private static ObjectMapper objectMapper;

	public String mapToJson(Object object) throws JsonProcessingException {
		return objectMapper.writeValueAsString(object);
	}

	private final Student STUDENT = new Student(1, "Pragya", "Jain", "8743958309", "pragya@gmail.com", "Pragya@12",
			"Pragya@12", null, null, true, "STUDENT");
	private final Course COURSE = new Course("IT1001", "Java Programming", "Java", new Category("IT", "IT", null), 48.0,
			4500.0, 0, 0.0, "image", "English", null, null, null, null);
	private final WishlistDto WISHLIST_DTO = new WishlistDto(1, STUDENT.getStudentId(),
			Arrays.asList(COURSE.getCourseId()), 0.0);
	private final Wishlist WISHLIST = new Wishlist(1, STUDENT, Arrays.asList(COURSE), 0.0);

	@BeforeAll
	public static void setUp() {
		objectMapper = new ObjectMapper();
	}

	@Test
	void testInsertCourseIntoWishlist() throws Exception {
		when(wishlistService.insertCourseIntoWishlist("IT1001", 1)).thenReturn(WISHLIST_DTO);
		mockMvc.perform(post("/learnvilla/api/wishlist/insert-course/{studentId}/{courseId}", 1, "IT1001")
				.contentType(MediaType.APPLICATION_JSON).content(mapToJson(WISHLIST_DTO))
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.wishlistId").value(1));
	}

	@Test
	void testDeleteCourseFromWishlist() throws Exception {
		when(wishlistService.deleteCourseFromWishlist("IT1001", 1)).thenReturn(WISHLIST_DTO);
		mockMvc.perform(delete("/learnvilla/api/wishlist/delete-course/{studentId}/{courseId}", 1, "IT1001")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.wishlistId").value(1));

	}

	@Test
	void testViewWishlistByStudentId() throws Exception {
		when(wishlistService.viewWishlistByStudentId(1)).thenReturn(WISHLIST);
		mockMvc.perform(get("/learnvilla/api/wishlist/view-wishlist/{studentId}", 1).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(jsonPath("$.student.studentId").value(1));
	}

	@Test
	void testDeleteWishlistByStudentId() throws Exception {
		when(wishlistService.deleteWishlistByStudentId(1)).thenReturn(1);
		mockMvc.perform(delete("/learnvilla/api/wishlist/delete-wishlist/{studentId}", 1).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$").value("Wishlist ID 1 deleted of student's ID 1"));

	}

}
