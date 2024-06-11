package com.cts.learnvilla.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.learnvilla.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

	/**
	 * custom finder method to find student by given email ID
	 * 
	 * @param email
	 * @return
	 */
	public Student findByEmail(String email);

	/**
	 * custom finder method to find student by given mobile number
	 * 
	 * @param mobile
	 * @return
	 */
	public Student findByMobile(String mobile);

}
