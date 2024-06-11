package com.cts.learnvilla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.learnvilla.dto.PaymentDto;
import com.cts.learnvilla.model.Payment;
import com.cts.learnvilla.service.PaymentService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/learnvilla/api/payments")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	/**
	 * Used to add a payment
	 * 
	 * @param paymentDto
	 * @return
	 */
	@PostMapping("/add-payment")
	public ResponseEntity<PaymentDto> addPayment(@Valid @RequestBody PaymentDto paymentDto) {
		PaymentDto addedPaymentDto = paymentService.addPayment(paymentDto);
		return new ResponseEntity<>(addedPaymentDto, HttpStatus.CREATED);
	}

	/**
	 * Used to view all payments
	 * 
	 * @return
	 */
	@GetMapping("/view-all-payments")
	public ResponseEntity<List<Payment>> viewAllPayments() {
		List<Payment> payments = paymentService.viewAllPayments();
		return new ResponseEntity<>(payments, HttpStatus.OK);
	}

	/**
	 * Used to view payment by student ID
	 * 
	 * @param studentId
	 * @return
	 */
	@GetMapping("/view-all-payments/student-id/{studentId}")
	public ResponseEntity<List<Payment>> viewPaymentByStudentId(@PathVariable Integer studentId) {
		List<Payment> payments = paymentService.viewPaymentsByStudentId(studentId);
		return new ResponseEntity<>(payments, HttpStatus.OK);
	}

	/**
	 * Used to view payment by payment ID
	 * 
	 * @param paymentId
	 * @return
	 */
	@GetMapping("/view-payment/{paymentId}")
	public ResponseEntity<Payment> viewPaymentByPaymentId(@PathVariable Integer paymentId) {
		Payment payment = paymentService.viewPaymentByPaymentId(paymentId);
		return new ResponseEntity<>(payment, HttpStatus.OK);
	}

	/**
	 * Used to update payment
	 * 
	 * @param paymentId
	 * @param status
	 * @return
	 */
	@PutMapping("/update-payment-status/{paymentId}/{status}")
	public ResponseEntity<PaymentDto> updatePaymentStatus(@PathVariable Integer paymentId,
			@PathVariable Boolean status) {
		PaymentDto paymentDto = paymentService.updatePaymentStatus(paymentId, status);
		return new ResponseEntity<>(paymentDto, HttpStatus.OK);
	}

}
