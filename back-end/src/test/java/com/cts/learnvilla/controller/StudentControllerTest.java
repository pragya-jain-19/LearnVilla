package com.cts.learnvilla.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.cts.learnvilla.dto.StudentDto;
import com.cts.learnvilla.model.Student;
import com.cts.learnvilla.service.impl.StudentServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc(addFilters = false)
public class StudentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StudentServiceImpl studentService;

	private static ObjectMapper objectMapper;

	public String mapToJson(Object object) throws JsonProcessingException {
		return objectMapper.writeValueAsString(object);
	}

	private final StudentDto STUDENT_DTO = new StudentDto(1, "Pragya", "Jain", "8743958309", "pragya@gmail.com",
			"Pragya@12", "Pragya@12", true, "STUDENT");
	private final Student STUDENT = new Student(1, "Pragya", "Jain", "8743958309", "pragya@gmail.com", "Pragya@12",
			"Pragya@12", null, null, true, "STUDENT");
	private final List<Student> STUDENTS = Arrays.asList(STUDENT);

	@BeforeAll
	public static void setUp() {
		objectMapper = new ObjectMapper();
	}

	@Test
	void testViewAllStudents() throws Exception {
		when(studentService.viewAllStudents()).thenReturn(STUDENTS);
		mockMvc.perform(get("/learnvilla/api/students/view-all-students").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(1));
	}

	@Test
	void testViewStudentById() throws Exception {
		when(studentService.viewStudentById(1)).thenReturn(STUDENT);
		mockMvc.perform(get("/learnvilla/api/students/view-student/{studentId}", 1).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(jsonPath("$.studentId").value(1));
	}

	@Test
	void testUpdateStudent() throws Exception {
		when(studentService.updateStudent(STUDENT_DTO)).thenReturn(STUDENT_DTO);
		mockMvc.perform(put("/learnvilla/api/students/update-student").contentType(MediaType.APPLICATION_JSON)
				.content(mapToJson(STUDENT_DTO)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.studentId").value(1));
	}

	@Test
	void testDeleteStudent() throws Exception {
		when(studentService.deleteStudent(1)).thenReturn(1);
		mockMvc.perform(delete("/learnvilla/api/students/delete-student/{studentId}", 1).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$").value("Student deleted with the ID : 1"));

	}

}
