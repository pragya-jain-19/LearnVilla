package com.cts.learnvilla.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc(addFilters = false)
public class LoginControllerTest {

//	@Autowired
//	private MockMvc mockMvc;
//
//	@MockBean
//	private StudentService studentService;
//
//	private static ObjectMapper objectMapper;
//
//	public String mapToJson(Object object) throws JsonProcessingException {
//		return objectMapper.writeValueAsString(object);
//	}
//
//	private final LoginCredentialsDto LOGIN_CREDENTIALS_DTO = new LoginCredentialsDto(1, "pragya@gmail.com",
//			"Pragya@12", "Pragya@12", "STUDENT");
//	private final StudentDto STUDENT_DTO = new StudentDto(1, "Pragya", "Jain", "8743958309", "pragya@gmail.com",
//			"Pragya@12", "Pragya@12", true, "STUDENT");
//
//	@BeforeAll
//	public static void setUp() {
//		objectMapper = new ObjectMapper();
//	}
//
//	@Test
//	void testLoginStudent() throws Exception {
//		when(studentService.loginStudent(LOGIN_CREDENTIALS_DTO)).thenReturn(true);
//		mockMvc.perform(post("/learnvilla/api/login").contentType(MediaType.APPLICATION_JSON)
//				.content(mapToJson(LOGIN_CREDENTIALS_DTO)).accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk()).andExpect(jsonPath("$").value(true));
//	}
//
//	@Test
//	void testRegisterStudent() throws Exception {
//		when(studentService.registerStudent(STUDENT_DTO)).thenReturn(STUDENT_DTO);
//		mockMvc.perform(post("/learnvilla/api/register").contentType(MediaType.APPLICATION_JSON)
//				.content(mapToJson(STUDENT_DTO)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
//				.andExpect(jsonPath("$.studentId").value(1));
//	}

}
