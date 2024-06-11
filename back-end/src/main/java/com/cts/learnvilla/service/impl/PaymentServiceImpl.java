package com.cts.learnvilla.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.learnvilla.dto.PaymentDto;
import com.cts.learnvilla.exception.PaymentNotDoneException;
import com.cts.learnvilla.exception.ResourceAlreadyExistsException;
import com.cts.learnvilla.exception.ResourceNotFoundException;
import com.cts.learnvilla.model.Course;
import com.cts.learnvilla.model.Enrollment;
import com.cts.learnvilla.model.Payment;
import com.cts.learnvilla.model.Student;
import com.cts.learnvilla.repository.CourseRepository;
import com.cts.learnvilla.repository.EnrollmentRepository;
import com.cts.learnvilla.repository.PaymentRepository;
import com.cts.learnvilla.repository.StudentRepository;
import com.cts.learnvilla.service.PaymentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private EnrollmentRepository enrollmentRepository;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Used to calculate payment amount of all the courses
	 * 
	 * @param courses
	 * @return
	 */
	private Double calculateAmount(List<Course> courses) {
		log.debug("Calculating price of courses");
		courses.forEach(c -> log.debug(c.getCourseId() + " " + c.getPrice()));
		Double price = 0.0;
		for (Course course : courses) {
			price += course.getPrice();
		}
		log.debug("Calculated price of courses : {}", price);
		return price;
	}

	/**
	 * Used to add a payment
	 * 
	 * @param paymentDto
	 * @return
	 * @throws ResourceAlreadyExistsException
	 * @throws ResourceNotFoundException
	 */
	@Override
	public PaymentDto addPayment(PaymentDto paymentDto) throws ResourceNotFoundException, ResourceAlreadyExistsException {
		log.info("Adding payment");
		Payment payment = dtoToPayment(paymentDto);
		Optional<Student> student = studentRepository.findById(payment.getStudent().getStudentId());
		if (student.isEmpty()) {
			throw new ResourceNotFoundException(
					"Student ID " + payment.getStudent().getStudentId() + " doesn't exists");
		}
		payment.setStudent(student.get());
		List<Enrollment> enrollments = enrollmentRepository.findByStudent_StudentId(student.get().getStudentId());
		List<Course> courses = new ArrayList<>();
		for (String s : paymentDto.getCourseIds()) {
			Optional<Course> c = courseRepository.findById(s);
			if (c.isEmpty()) {
				throw new ResourceNotFoundException("Course ID " + s + " doesn't exists");
			}
			for(Enrollment e : enrollments) {
				if(e.getCourse().getCourseId().equals(c.get().getCourseId())) {
					throw new ResourceAlreadyExistsException("You are already enrolled in course " + c.get().getCourseId());
				}
			}
			courses.add(c.get());
		}
		payment.setCourses(courses);
		payment.setAmount(calculateAmount(payment.getCourses()));
		Payment paymentAdded = paymentRepository.save(payment);
		PaymentDto paymentDtoAdded = paymentToDto(paymentAdded);
		paymentDtoAdded.setCourseIds(paymentDto.getCourseIds());
		log.debug("Payment Added : {}", paymentDtoAdded);
		log.info("Payment Added successfully");
		return paymentDtoAdded;
	}

	/**
	 * Used to view all payments
	 * 
	 * @return
	 * @throws PaymentNotDoneException
	 */
	@Override
	public List<Payment> viewAllPayments() throws PaymentNotDoneException {
		log.info("Viewing All Payments");
		List<Payment> payments = paymentRepository.findAll();
		if (payments.size() == 0) {
			throw new PaymentNotDoneException("No payments present");
		}
		log.debug("Payments present : ");
		payments.forEach(p -> log.debug("Payment ID {} : Rs. {}/-", p.getPaymentId(), p.getAmount()));
		log.info("All payments viewed successfully");
		return payments;
	}

	/**
	 * Used to view payments by student ID
	 * 
	 * @param studentId
	 * @return
	 * @throws PaymentNotDoneException
	 * @throws ResourceNotFoundException
	 */
	@Override
	public List<Payment> viewPaymentsByStudentId(Integer studentId)
			throws ResourceNotFoundException, PaymentNotDoneException {
		log.info("Viewing Payment of Student ID : {}", studentId);
		Optional<Student> student = studentRepository.findById(studentId);
		if (student.isEmpty()) {
			throw new ResourceNotFoundException("Student not found with student ID " + studentId);
		}
		List<Payment> payments = paymentRepository.findByStudent_StudentId(studentId);
		if (payments.size() == 0) {
			throw new PaymentNotDoneException("No payments done by student " + studentId);
		}
		log.debug("Payments done by Student {} : ", studentId);
		payments.forEach(p -> log.debug("Payment ID {} : Rs. {}/-", p.getPaymentId(), p.getAmount()));
		log.info("Viewed Payment of Student {} successfully", studentId);
		return payments;
	}

	/**
	 * Used to view a payment by payment ID
	 * 
	 * @param paymentId
	 * @return
	 * @throws PaymentNotDoneException
	 */
	@Override
	public Payment viewPaymentByPaymentId(Integer paymentId) throws PaymentNotDoneException {
		log.info("Viewing Payment of ID : {}", paymentId);
		Optional<Payment> p = paymentRepository.findById(paymentId);
		if (p.isEmpty()) {
			throw new PaymentNotDoneException("Payment ID " + paymentId + " doesn't exists");
		}
		Payment payment = p.get();
		log.debug("Payment ID viewed {}, Rs. {}/-", payment.getPaymentId(), payment.getAmount());
		log.info("Viewed Payment {} successfully", paymentId);
		return payment;
	}

	/**
	 * Used to update payment status
	 * 
	 * @param paymentId
	 * @param status
	 * @return
	 * @throws PaymentNotDoneException
	 */
	@Override
	public PaymentDto updatePaymentStatus(Integer paymentId, Boolean status) throws PaymentNotDoneException {
		log.info("Updating Payment");
		Optional<Payment> p = paymentRepository.findById(paymentId);
		if (p.isEmpty()) {
			throw new PaymentNotDoneException("Payment ID " + paymentId + " doesn't exists");
		}
		Payment payment = p.get();
		payment.setStatus(status);
		payment.setStatusUpdatedTime(LocalDateTime.now());
		Payment updatedPayment = paymentRepository.save(payment);
		PaymentDto updatedPaymentDto = paymentToDto(updatedPayment);
		List<String> courseIds = new ArrayList<>();
		for (Course c : p.get().getCourses()) {
			courseIds.add(c.getCourseId());
		}
		updatedPaymentDto.setCourseIds(courseIds);
		log.debug("Payment Updated {}", updatedPaymentDto);
		log.info("Payment Updated successfully");
		return updatedPaymentDto;
	}

	/**
	 * Converts PaymentDto to Payment object
	 * 
	 * @param paymentDto
	 * @return
	 */
	public Payment dtoToPayment(PaymentDto paymentDto) {
		return this.modelMapper.map(paymentDto, Payment.class);
	}

	/**
	 * Converts Payment to PaymentDto object
	 * 
	 * @param payment
	 * @return
	 */
	public PaymentDto paymentToDto(Payment payment) {
		return this.modelMapper.map(payment, PaymentDto.class);
	}

}
