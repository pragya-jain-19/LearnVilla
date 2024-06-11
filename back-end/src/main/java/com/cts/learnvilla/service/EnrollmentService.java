package com.cts.learnvilla.service;

import java.util.List;

import com.cts.learnvilla.dto.EnrollmentDto;
import com.cts.learnvilla.exception.ResourceAlreadyExistsException;
import com.cts.learnvilla.exception.ResourceNotFoundException;

public interface EnrollmentService {

	/**
	 * Used to add enrollment
	 * 
	 * @param enrollmentDto
	 * @throws ResourceNotFoundException
	 * @throws ResourceAlreadyExistsException
	 * @return
	 */
	public EnrollmentDto addEnrollment(EnrollmentDto enrollmentDto)
			throws ResourceNotFoundException, ResourceAlreadyExistsException;

	/**
	 * Used to update enrollment
	 * 
	 * @param enrollmentDto
	 * @return
	 * @throws ResourceNotFoundException
	 * @throws ResourceAlreadyExistsException
	 */
	public EnrollmentDto updateEnrollment(EnrollmentDto enrollmentDto)
			throws ResourceNotFoundException, ResourceAlreadyExistsException;

	/**
	 * Used to update completion status of course
	 * 
	 * @param enrollmentDto
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public EnrollmentDto completionUpdate(EnrollmentDto enrollmentDto) throws ResourceNotFoundException;

	/**
	 * Used to view all enrollments
	 * 
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public List<EnrollmentDto> viewAllEnrollments() throws ResourceNotFoundException;

	/**
	 * Used to view all enrollments of a particular student, given studentId
	 * 
	 * @param studentId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public List<EnrollmentDto> viewAllEnrollmentsByStudentId(Integer studentId) throws ResourceNotFoundException;

	/**
	 * Used to view all enrollments in a particular course, given courseId
	 * 
	 * @param courseId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public List<EnrollmentDto> viewAllEnrollmentsByCourseId(String courseId) throws ResourceNotFoundException;

	/**
	 * Used to view enrollment by ID
	 * 
	 * @param enrollmentId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public EnrollmentDto viewEnrollmentById(Integer enrollmentId) throws ResourceNotFoundException;

	/**
	 * Used to delete enrollment
	 * 
	 * @param enrollmentId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public Integer deleteEnrollment(Integer enrollmentId) throws ResourceNotFoundException;

}
