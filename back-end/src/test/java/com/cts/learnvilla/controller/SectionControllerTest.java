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

import com.cts.learnvilla.dto.SectionDto;
import com.cts.learnvilla.model.Category;
import com.cts.learnvilla.model.Course;
import com.cts.learnvilla.model.Section;
import com.cts.learnvilla.service.SectionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc(addFilters = false)
public class SectionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SectionService sectionService;

	private static ObjectMapper objectMapper;

	public String mapToJson(Object object) throws JsonProcessingException {
		return objectMapper.writeValueAsString(object);
	}

	private final SectionDto SECTION_DTO_1 = new SectionDto(1, "IT1001", 1, "Java Basics", "Java Basics", "java video");
	private final Section SECTION_1 = new Section(1,
			new Course("IT1001", "Java Programming", "Java", new Category("IT", "IT", null), 48.0, 4500.0, 0, 0.0,
					"image", "English", null, null, null, null),
			1, "Java Basics", "Java Basics", "java video", null, null);

	@BeforeAll
	public static void setUp() {
		objectMapper = new ObjectMapper();
	}

	@Test
	void testAddSection() throws Exception {
		when(sectionService.addSection(SECTION_DTO_1)).thenReturn(SECTION_DTO_1);

		mockMvc.perform(post("/learnvilla/api/sections/add-section").contentType(MediaType.APPLICATION_JSON)
				.content(mapToJson(SECTION_DTO_1)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.sectionId").value(1));
	}

	@Test
	void testViewAllSectionsByCourseId() throws Exception {
		when(sectionService.viewAllSectionsByCourseId("IT1001")).thenReturn(Arrays.asList(SECTION_1));
		mockMvc.perform(
				get("/learnvilla/api/sections/view-all-sections/course-id/{courseId}", "IT1001").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(jsonPath("$.length()").value(1));
	}

	@Test
	void testViewAllSectionsByCourseName() throws Exception {
		when(sectionService.viewAllSectionsByCourseName("Java Programming")).thenReturn(Arrays.asList(SECTION_1));
		mockMvc.perform(get("/learnvilla/api/sections/view-all-sections/course-name/{courseName}", "Java Programming")
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.length()").value(1));
	}

	@Test
	void testViewSectionById() throws Exception {
		when(sectionService.viewSectionById(1)).thenReturn(SECTION_DTO_1);
		mockMvc.perform(get("/learnvilla/api/sections/view-section/{sectionId}", 1).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(jsonPath("$.sectionId").value(1));
	}

	@Test
	void testUpdateCourse() throws Exception {
		when(sectionService.updateSection(SECTION_DTO_1)).thenReturn(SECTION_DTO_1);
		mockMvc.perform(put("/learnvilla/api/sections/update-section").contentType(MediaType.APPLICATION_JSON)
				.content(mapToJson(SECTION_DTO_1)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.sectionId").value(1));
	}

	@Test
	void testDeleteCourse() throws Exception {
		when(sectionService.deleteSectionByCourseIdAndSectionNumber("IT1001", 1)).thenReturn(1);
		mockMvc.perform(delete("/learnvilla/api/sections/delete-section/course-id/{courseId}/{sectionNumber}", "IT1001", 1)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$").value("Section Number 1 deleted of Course ID : IT1001"));

	}

}
