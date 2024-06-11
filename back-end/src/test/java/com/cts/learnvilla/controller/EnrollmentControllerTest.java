package com.cts.learnvilla.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.cts.learnvilla.dto.EnrollmentDto;
import com.cts.learnvilla.model.Category;
import com.cts.learnvilla.model.Course;
import com.cts.learnvilla.model.Student;
import com.cts.learnvilla.service.EnrollmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc(addFilters = false)
public class EnrollmentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EnrollmentService enrollmentService;

	private static ObjectMapper objectMapper;

	public String mapToJson(Object object) throws JsonProcessingException {
		return objectMapper.writeValueAsString(object);
	}

	private final Student STUDENT = new Student(1, "Pragya", "Jain", "9384757382", "pragya@gmail.com", "Pragya@123",
			"Pragya@123", null, null, true, "STUDENT");
	private final Course COURSE = new Course("IT1001", "Java Programming", "Java", new Category("IT", "IT", null), 48.0,
			4500.0, 0, 0.0, "image", "English", null, null, null, null);
	private final EnrollmentDto ENROLLMENT_DTO = new EnrollmentDto(1, STUDENT.getStudentId(), COURSE.getCourseId(), 0.0,
			true, null);
	private final List<EnrollmentDto> ENROLLMENTS_DTO = Arrays.asList(ENROLLMENT_DTO);

	@BeforeAll
	public static void setUp() {
		objectMapper = new ObjectMapper();
	}

	@Test
	void testAddEnrollment() throws Exception {
		when(enrollmentService.addEnrollment(ENROLLMENT_DTO)).thenReturn(ENROLLMENT_DTO);
		mockMvc.perform(post("/learnvilla/api/enrollments/add-enrollment").contentType(MediaType.APPLICATION_JSON)
				.content(mapToJson(ENROLLMENT_DTO)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.enrollmentId").value(1));
	}

	@Test
	void testViewAllEnrollments() throws Exception {
		when(enrollmentService.viewAllEnrollments()).thenReturn(ENROLLMENTS_DTO);
		mockMvc.perform(get("/learnvilla/api/enrollments/view-all-enrollments").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(1));
	}

	@Test
	void testUpdateEnrollment() throws Exception {
		when(enrollmentService.updateEnrollment(ENROLLMENT_DTO)).thenReturn(ENROLLMENT_DTO);
		mockMvc.perform(put("/learnvilla/api/enrollments/update-enrollment").contentType(MediaType.APPLICATION_JSON)
				.content(mapToJson(ENROLLMENT_DTO)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.enrollmentId").value(1));
	}

	@Test
	void testCompletionUpdate() throws Exception {
		when(enrollmentService.completionUpdate(ENROLLMENT_DTO)).thenReturn(ENROLLMENT_DTO);
		mockMvc.perform(put("/learnvilla/api/enrollments/update-completion-status").contentType(MediaType.APPLICATION_JSON)
				.content(mapToJson(ENROLLMENT_DTO)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.enrollmentId").value(1));
	}

	@Test
	void testViewAllEnrollmentsByStudentId() throws Exception {
		when(enrollmentService.viewAllEnrollmentsByStudentId(1)).thenReturn(ENROLLMENTS_DTO);
		mockMvc.perform(get("/learnvilla/api/enrollments/view-all-enrollments/student/{studentId}", 1).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(jsonPath("$.length()").value(1));
	}

	@Test
	void testViewAllEnrollmentsByCourseId() throws Exception {
		when(enrollmentService.viewAllEnrollmentsByCourseId("IT1001")).thenReturn(ENROLLMENTS_DTO);
		mockMvc.perform(
				get("/learnvilla/api/enrollments/view-all-enrollments/course/{courseId}", "IT1001").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(jsonPath("$.length()").value(1));
	}

	@Test
	void testViewEnrollmentById() throws Exception {
		when(enrollmentService.viewEnrollmentById(1)).thenReturn(ENROLLMENT_DTO);
		mockMvc.perform(get("/learnvilla/api/enrollments/view-enrollment/{enrollmentId}", 1).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(jsonPath("$.enrollmentId").value(1));
	}

	@Test
	void testDeleteEnrollment() throws Exception {
		when(enrollmentService.deleteEnrollment(1)).thenReturn(1);
		mockMvc.perform(delete("/learnvilla/api/enrollments/delete-enrollment/{enrollmentId}", 1).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$").value("Enrollment ID 1 deleted"));

	}

}
