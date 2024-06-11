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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.cts.learnvilla.dto.CourseDto;
import com.cts.learnvilla.model.Category;
import com.cts.learnvilla.model.Course;
import com.cts.learnvilla.service.CourseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc(addFilters = false)
public class CourseControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CourseService courseService;

	private static ObjectMapper objectMapper;

	public String mapToJson(Object object) throws JsonProcessingException {
		return objectMapper.writeValueAsString(object);
	}

	private final CourseDto COURSE_DTO_1 = new CourseDto("IT1001", "Java Programming", "Java", "IT", 48.0, 4500.0, 0,
			0.0, "image", "English");
	private final Course COURSE_1 = new Course("IT1001", "Java Programming", "Java", new Category("IT", "IT", null),
			48.0, 4500.0, 0, 0.0, "image", "English", null, null, null, null),
			COURSE_2 = new Course("IT1002", "Python Programming", "Python", new Category("IT", "IT", null), 48.0,
					4500.0, 0, 0.0, "image", "English", null, null, null, null);

	@BeforeAll
	public static void setUp() {
		objectMapper = new ObjectMapper();
	}

	@Test
	void testAddCourse() throws Exception {
		when(courseService.addCourse(COURSE_DTO_1)).thenReturn(COURSE_DTO_1);
		mockMvc.perform(post("/learnvilla/api/courses/add-course").contentType(MediaType.APPLICATION_JSON)
				.content(mapToJson(COURSE_DTO_1)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.courseId").value("IT1001"));
	}

	@Test
	void testViewAllCourses() throws Exception {
		when(courseService.viewAllCourses()).thenReturn(Arrays.asList(COURSE_1, COURSE_2));
		mockMvc.perform(get("/learnvilla/api/courses/view-all-courses").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2));
	}

	@Test
	void testViewCourseById() throws Exception {
		when(courseService.viewCourseById("IT1001")).thenReturn(COURSE_1);
		mockMvc.perform(get("/learnvilla/api/courses/view-course/course-id/{courseId}", "IT1001").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(jsonPath("$.courseId").value("IT1001"));
	}

	@Test
	void testViewCourseByName() throws Exception {
		when(courseService.viewCourseByName("Java Programming")).thenReturn(COURSE_1);
		mockMvc.perform(get("/learnvilla/api/courses/view-course/course-name/{courseName}", "Java Programming")
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.courseName").value("Java Programming"));
	}

	@Test
	void testViewCoursesByCategoryId() throws Exception {
		when(courseService.viewCoursesByCategoryId("IT")).thenReturn(Arrays.asList(COURSE_1, COURSE_2));
		mockMvc.perform(
				get("/learnvilla/api/courses/view-all-courses/category-id/{categoryId}", "IT").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(jsonPath("$.length()").value(2));
	}

	@Test
	void testUpdateCourse() throws Exception {
		when(courseService.updateCourse(COURSE_DTO_1)).thenReturn(COURSE_DTO_1);
		mockMvc.perform(put("/learnvilla/api/courses/update-course").contentType(MediaType.APPLICATION_JSON)
				.content(mapToJson(COURSE_DTO_1)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.courseId").value("IT1001"));
	}

	@Test
	void testDeleteCourse() throws Exception {
		when(courseService.deleteCourse("IT1001")).thenReturn("IT1001");
		mockMvc.perform(delete("/learnvilla/api/courses/delete-course/{courseId}", "IT1001").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$").value("Course deleted with ID : IT1001"));

	}

}
