package com.cts.learnvilla.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.learnvilla.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

	/**
	 * custom finder method to find payment by student ID
	 * 
	 * @param studentId
	 * @return
	 */
	public List<Payment> findByStudent_StudentId(Integer studentId);

}
