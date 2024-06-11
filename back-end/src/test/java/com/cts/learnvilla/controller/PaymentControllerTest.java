package com.cts.learnvilla.controller;

import static org.mockito.Mockito.when;
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

import com.cts.learnvilla.dto.PaymentDto;
import com.cts.learnvilla.model.Category;
import com.cts.learnvilla.model.Course;
import com.cts.learnvilla.model.Payment;
import com.cts.learnvilla.model.Student;
import com.cts.learnvilla.service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc(addFilters = false)
public class PaymentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PaymentService paymentService;

	private static ObjectMapper objectMapper;

	public String mapToJson(Object object) throws JsonProcessingException {
		return objectMapper.writeValueAsString(object);
	}

	private final Student STUDENT = new Student(1, "Pragya", "Jain", "8743958309", "pragya@gmail.com", "Pragya@12",
			"Pragya@12", null, null, true, "STUDENT");
	private final Course COURSE = new Course("IT1001", "Java Programming", "Java", new Category("IT", "IT", null),
			48.0, 4500.0, 0, 0.0, "image", "English", null, null, null, null);
	private final PaymentDto PAYMENT_DTO = new PaymentDto(1, STUDENT.getStudentId(), Arrays.asList(COURSE.getCourseId()), 0.0, true);
	private final Payment PAYMENT = new Payment(1, STUDENT, null, 0.0, true, null, null);
	private final List<Payment> PAYMENTS = Arrays.asList(PAYMENT);

	@BeforeAll
	public static void setUp() {
		objectMapper = new ObjectMapper();
	}

	@Test
	void testAddPayment() throws Exception {
		when(paymentService.addPayment(PAYMENT_DTO)).thenReturn(PAYMENT_DTO);
		mockMvc.perform(post("/learnvilla/api/payments/add-payment").contentType(MediaType.APPLICATION_JSON)
				.content(mapToJson(PAYMENT_DTO)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.paymentId").value(1));
	}

	@Test
	void testViewAllPayments() throws Exception {
		when(paymentService.viewAllPayments()).thenReturn(PAYMENTS);
		mockMvc.perform(get("/learnvilla/api/payments/view-all-payments").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(1));
	}

	@Test
	void testViewPaymentByPaymentId() throws Exception {
		when(paymentService.viewPaymentByPaymentId(1)).thenReturn(PAYMENT);
		mockMvc.perform(get("/learnvilla/api/payments/view-payment/{paymentId}", 1).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(jsonPath("$.paymentId").value(1));
	}

	@Test
	void testViewPaymentByStudentId() throws Exception {
		when(paymentService.viewPaymentsByStudentId(1)).thenReturn(PAYMENTS);
		mockMvc.perform(get("/learnvilla/api/payments/view-all-payments/student-id/{studentId}", 1)
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.length()").value(1));
	}

	@Test
	void testUpdatePaymentStatus() throws Exception {
		when(paymentService.updatePaymentStatus(1, true)).thenReturn(PAYMENT_DTO);
		mockMvc.perform(put("/learnvilla/api/payments/update-payment-status/{paymentId}/{status}", 1, true)
				.contentType(MediaType.APPLICATION_JSON).content(mapToJson(PAYMENT_DTO))
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.paymentId").value(1));
	}

}
