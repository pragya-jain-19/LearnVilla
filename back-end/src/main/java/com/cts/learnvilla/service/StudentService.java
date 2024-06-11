package com.cts.learnvilla.service;

import java.util.List;

import com.cts.learnvilla.dto.LoginCredentialsDto;
import com.cts.learnvilla.dto.StudentDto;
import com.cts.learnvilla.exception.InvalidCredentials;
import com.cts.learnvilla.exception.ResourceAlreadyExistsException;
import com.cts.learnvilla.exception.ResourceNotFoundException;
import com.cts.learnvilla.model.Student;

public interface StudentService {

	/**
	 * Used to register student
	 * 
	 * @param studentDto
	 * @return
	 * @throws ResourceAlreadyExistsException
	 * @throws InvalidCredentials
	 */
	public StudentDto registerStudent(StudentDto studentDto) throws ResourceAlreadyExistsException, InvalidCredentials;

	/**
	 * Used to login student
	 * 
	 * @param loginCredentialsDto
	 * @return
	 * @throws InvalidCredentials
	 * @throws ResourceNotFoundException
	 */
	public Boolean loginStudent(LoginCredentialsDto loginCredentialsDto)
			throws InvalidCredentials, ResourceNotFoundException;

	/**
	 * Used to view all students
	 * 
	 * @throws ResourceNotFoundException
	 * @return
	 */
	public List<Student> viewAllStudents() throws ResourceNotFoundException;

	/**
	 * Used to view student by ID
	 * 
	 * @param studentId
	 * @throws ResourceNotFoundException
	 * @return
	 */
	public Student viewStudentById(Integer studentId) throws ResourceNotFoundException;

	/**
	 * Used to update student
	 * 
	 * @param studentDto
	 * @throws ResourceNotFoundException
	 * @return
	 */
	public StudentDto updateStudent(StudentDto studentDto) throws ResourceNotFoundException;

	/**
	 * Used to delete a student
	 * 
	 * @param studentId
	 * @throws ResourceNotFoundException
	 * @return
	 */
	public Integer deleteStudent(Integer studentId) throws ResourceNotFoundException;

}
