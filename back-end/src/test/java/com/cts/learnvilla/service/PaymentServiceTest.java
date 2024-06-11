package com.cts.learnvilla.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import com.cts.learnvilla.dto.PaymentDto;
import com.cts.learnvilla.exception.PaymentNotDoneException;
import com.cts.learnvilla.exception.ResourceNotFoundException;
import com.cts.learnvilla.model.Category;
import com.cts.learnvilla.model.Course;
import com.cts.learnvilla.model.Enrollment;
import com.cts.learnvilla.model.Payment;
import com.cts.learnvilla.model.Student;
import com.cts.learnvilla.repository.CourseRepository;
import com.cts.learnvilla.repository.EnrollmentRepository;
import com.cts.learnvilla.repository.PaymentRepository;
import com.cts.learnvilla.repository.StudentRepository;
import com.cts.learnvilla.service.impl.PaymentServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

	private final Student STUDENT = new Student(1, "Pragya", "Jain", "8743958309", "pragya@gmail.com", "Pragya@12",
			"Pragya@12", null, null, true, "STUDENT");
	private final Category CATEGORY = new Category("IT", "IT", null);
	private final Course COURSE = new Course("IT1001", "Java Programming", "Java", CATEGORY, 48.0, 4500.0, 0, 0.0,
			"image", "English", null, null, null, null);
	private final Course COURSE2 = new Course("IT1002", "Java Programming", "Java", CATEGORY, 48.0, 4500.0, 0, 0.0,
			"image", "English", null, null, null, null);
	private final List<Course> COURSES = Arrays.asList(COURSE);
//	private final Course COURSES2 = Arrays.asList(COURSE2);
	private final List<String> COURSE_IDS = Arrays.asList("IT1001");
	private final Payment PAYMENT = new Payment(1, STUDENT, COURSES, 0.0, true, null, null);
	private final List<Payment> PAYMENTS = Arrays.asList(PAYMENT);
	private final PaymentDto PAYMENT_DTO = new PaymentDto(1, STUDENT.getStudentId(), COURSE_IDS, 0.0, true);
	private final Enrollment ENROLLMENT = new Enrollment(1, STUDENT, COURSE2, null, null,0.0, true);
	private final List<Enrollment> ENROLLMENTS = Arrays.asList(ENROLLMENT);

	@Mock
	private ModelMapper modelMapper;

	@Mock
	private PaymentRepository paymentRepository;

	@Mock
	private StudentRepository studentRepository;
	
	@Mock
	private EnrollmentRepository enrollmentRepository;

	@Mock
	private CourseRepository courseRepository;

	@InjectMocks
	private PaymentServiceImpl paymentService;

	@Test
	public void testAddPayment_ValidPayment() throws ResourceNotFoundException, PaymentNotDoneException {
		when(modelMapper.map(PAYMENT_DTO, Payment.class)).thenReturn(PAYMENT);
		when(modelMapper.map(PAYMENT, PaymentDto.class)).thenReturn(PAYMENT_DTO);
		when(studentRepository.findById(1)).thenReturn(Optional.of(STUDENT));
		when(enrollmentRepository.findByStudent_StudentId(1)).thenReturn(ENROLLMENTS);
		when(courseRepository.findById("IT1001")).thenReturn(Optional.of(COURSE));
		when(paymentRepository.save(PAYMENT)).thenReturn(PAYMENT);
		PaymentDto result = paymentService.addPayment(PAYMENT_DTO);
		assertNotNull(result);
		assertEquals(PAYMENT_DTO.getPaymentId(), result.getPaymentId());
	}

	@Test
	public void testAddPayment_InvalidPayment_InvalidStudent()
			throws ResourceNotFoundException, PaymentNotDoneException {
		when(modelMapper.map(PAYMENT_DTO, Payment.class)).thenReturn(PAYMENT);
		when(studentRepository.findById(1)).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			paymentService.addPayment(PAYMENT_DTO);
		});
		assertEquals("Student ID 1 doesn't exists", exception.getMessage());
	}

	@Test
	public void testAddPayment_InvalidPayment_InvalidCourse()
			throws ResourceNotFoundException, PaymentNotDoneException {
		when(modelMapper.map(PAYMENT_DTO, Payment.class)).thenReturn(PAYMENT);
		when(studentRepository.findById(1)).thenReturn(Optional.of(STUDENT));
		when(courseRepository.findById("IT1001")).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			paymentService.addPayment(PAYMENT_DTO);
		});
		assertEquals("Course ID IT1001 doesn't exists", exception.getMessage());
	}

	@Test
	public void testViewAllPayments_ValidPayments() throws PaymentNotDoneException {
		when(paymentRepository.findAll()).thenReturn(PAYMENTS);
		List<Payment> result = paymentService.viewAllPayments();
		assertEquals(PAYMENTS, result);
	}

	@Test
	public void testViewAllPayments_NoPayments() throws PaymentNotDoneException {
		List<Payment> payments = new ArrayList<>();
		when(paymentRepository.findAll()).thenReturn(payments);
		Exception exception = assertThrows(PaymentNotDoneException.class, () -> {
			paymentService.viewAllPayments();
		});
		assertEquals("No payments present", exception.getMessage());
	}

	@Test
	public void testViewPaymentsByStudentId_ValidCase() throws ResourceNotFoundException, PaymentNotDoneException {
		when(studentRepository.findById(1)).thenReturn(Optional.of(STUDENT));
		when(paymentRepository.findByStudent_StudentId(1)).thenReturn(PAYMENTS);
		List<Payment> result = paymentService.viewPaymentsByStudentId(1);
		assertEquals(PAYMENTS, result);
	}

	@Test
	public void testViewPaymentsByStudentId_InvalidStudent() throws ResourceNotFoundException, PaymentNotDoneException {
		when(studentRepository.findById(1)).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			paymentService.viewPaymentsByStudentId(1);
		});
		assertEquals("Student not found with student ID 1", exception.getMessage());
	}

	@Test
	public void testViewPaymentsByStudentId_NoPayments() throws ResourceNotFoundException, PaymentNotDoneException {
		when(studentRepository.findById(1)).thenReturn(Optional.of(STUDENT));
		when(paymentRepository.findByStudent_StudentId(1)).thenReturn(new ArrayList<>());
		Exception exception = assertThrows(PaymentNotDoneException.class, () -> {
			paymentService.viewPaymentsByStudentId(1);
		});
		assertEquals("No payments done by student 1", exception.getMessage());
	}

	@Test
	public void testViewPaymentByPaymentId_ValidCase() throws ResourceNotFoundException, PaymentNotDoneException {
		when(paymentRepository.findById(1)).thenReturn(Optional.of(PAYMENT));
		Payment result = paymentService.viewPaymentByPaymentId(1);
		assertEquals(PAYMENT, result);
	}

	@Test
	public void testViewPaymentByPaymentId_NoPayments() throws ResourceNotFoundException, PaymentNotDoneException {
		when(paymentRepository.findById(1)).thenReturn(Optional.empty());
		Exception exception = assertThrows(PaymentNotDoneException.class, () -> {
			paymentService.viewPaymentByPaymentId(1);
		});
		assertEquals("Payment ID 1 doesn't exists", exception.getMessage());
	}

	@Test
	public void testUpdatePaymentStatus_ValidCourse() throws ResourceNotFoundException {
		when(paymentRepository.findById(1)).thenReturn(Optional.of(PAYMENT));
		when(paymentRepository.save(PAYMENT)).thenReturn(PAYMENT);
		when(modelMapper.map(PAYMENT, PaymentDto.class)).thenReturn(PAYMENT_DTO);
		PaymentDto result = paymentService.updatePaymentStatus(1, true);
		assertNotNull(result);
		assertEquals(PAYMENT_DTO.getPaymentId(), result.getPaymentId());
	}

	@Test
	public void testUpdatePaymentStatus_InvalidPayment() throws ResourceNotFoundException {
		when(paymentRepository.findById(1)).thenReturn(Optional.empty());
		Exception exception = assertThrows(PaymentNotDoneException.class, () -> {
			paymentService.updatePaymentStatus(1, true);
		});
		assertEquals("Payment ID 1 doesn't exists", exception.getMessage());
	}

}
