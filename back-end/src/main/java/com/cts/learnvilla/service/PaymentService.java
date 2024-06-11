package com.cts.learnvilla.service;

import java.util.List;

import com.cts.learnvilla.dto.PaymentDto;
import com.cts.learnvilla.exception.PaymentNotDoneException;
import com.cts.learnvilla.exception.ResourceAlreadyExistsException;
import com.cts.learnvilla.exception.ResourceNotFoundException;
import com.cts.learnvilla.model.Payment;

public interface PaymentService {

	/**
	 * Used to add a payment
	 * 
	 * @param paymentDto
	 * @return
	 * @throws ResourceAlreadyExistsException
	 * @throws ResourceNotFoundException
	 */
	public PaymentDto addPayment(PaymentDto paymentDto) throws ResourceAlreadyExistsException, ResourceNotFoundException;

	/**
	 * Used to view all payments
	 * 
	 * @return
	 * @throws PaymentNotDoneException
	 */
	public List<Payment> viewAllPayments() throws PaymentNotDoneException;

	/**
	 * Used to view payments by student ID
	 * 
	 * @param studentId
	 * @return
	 * @throws PaymentNotDoneException
	 * @throws ResourceNotFoundException
	 */
	public List<Payment> viewPaymentsByStudentId(Integer studentId)
			throws PaymentNotDoneException, ResourceNotFoundException;

	/**
	 * Used to view a payment by payment ID
	 * 
	 * @param paymentId
	 * @return
	 * @throws PaymentNotDoneException
	 */
	public Payment viewPaymentByPaymentId(Integer paymentId) throws PaymentNotDoneException;

	/**
	 * Used to update payment status
	 * 
	 * @param paymentId
	 * @param status
	 * @return
	 * @throws PaymentNotDoneException
	 */
	public PaymentDto updatePaymentStatus(Integer paymentId, Boolean status) throws PaymentNotDoneException;

}
