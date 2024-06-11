package com.cts.learnvilla.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cts.learnvilla.dto.LoginCredentialsDto;
import com.cts.learnvilla.dto.StudentDto;
import com.cts.learnvilla.exception.InvalidCredentials;
import com.cts.learnvilla.exception.ResourceAlreadyExistsException;
import com.cts.learnvilla.exception.ResourceNotFoundException;
import com.cts.learnvilla.model.Student;
import com.cts.learnvilla.repository.StudentRepository;
import com.cts.learnvilla.service.StudentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService , UserDetailsService {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Used to register student
	 * 
	 * @param studentDto
	 * @return
	 * @throws ResourceAlreadyExistsException
	 * @throws InvalidCredentials
	 */
	@Override
	public StudentDto registerStudent(StudentDto studentDto) throws ResourceAlreadyExistsException, InvalidCredentials {
		log.info("Registering a new Student");
		Student student = this.dtoToStudent(studentDto);
		Student retrievedStudentByEmail = studentRepository.findByEmail(student.getEmail());
		if (retrievedStudentByEmail != null) {
			throw new ResourceAlreadyExistsException("Student already exists. Please login using your credentials.");
		}
		Student retrievedStudentByMobile = studentRepository.findByMobile(student.getMobile());
		if (retrievedStudentByMobile != null) {
			throw new ResourceAlreadyExistsException(
					"Mobile number already exists. Please register uisng different number.");
		}
		if (!student.getPassword().equals(student.getConfirmPassword())) {
			throw new InvalidCredentials("Password should match with confirm password");
		}
		Student addedStudent = studentRepository.save(student);
		StudentDto addedStudentDto = studentToDto(addedStudent);
		log.debug("Registered Student : {} ", addedStudentDto);
		log.info("Registered a new Student successfully");
		return addedStudentDto;
	}

	/**
	 * Used to login student
	 * 
	 * @param loginCredentialsDto
	 * @return
	 * @throws InvalidCredentials
	 * @throws ResourceNotFoundException
	 */
	@Override
	public Boolean loginStudent(LoginCredentialsDto loginCredentialsDto)
			throws InvalidCredentials, ResourceNotFoundException {
		log.info("Login Student");
		Student student = this.loginDtoToStudent(loginCredentialsDto);
		boolean login = false;
		if (!student.getPassword().equals(student.getConfirmPassword())) {
			log.error("Login failed!");
			throw new InvalidCredentials("Password field should match with confirm password!");
		}
		Student s = studentRepository.findByEmail(student.getEmail());
		if (s == null) {
			log.error("Login failed!");
			throw new ResourceNotFoundException("Student credentials not found");
		}
		if (student.getEmail().equals(s.getEmail()) && student.getPassword().equals(s.getPassword())) {
			login = true;
		} else {
			log.error("Login failed!");
			throw new InvalidCredentials("Invalid Credentials!");
		}
		log.debug("Student Login Details : {} {}", loginCredentialsDto.getEmail(), loginCredentialsDto.getRole());
		log.info("Student Logged in successfully");
		return login;
	}

	/**
	 * Used to view all students
	 * 
	 * @throws ResourceNotFoundException
	 * @return
	 */
	@Override
	public List<Student> viewAllStudents() throws ResourceNotFoundException {
		log.info("Viewing All Students");
		List<Student> students = studentRepository.findAll();
		if (students.size() == 0) {
			throw new ResourceNotFoundException("No students present");
		}
		log.debug("Students registered : ");
		students.forEach(s -> log.debug("{}. {}", s.getStudentId(), s.getEmail()));
		log.info("All Students Viewed successfully");
		return students;
	}

	/**
	 * Used to view student by ID
	 * 
	 * @param studentId
	 * @throws ResourceNotFoundException
	 * @return
	 */
	@Override
	public Student viewStudentById(Integer studentId) throws ResourceNotFoundException {
		log.info("Viewing Student by ID {}", studentId);
		Optional<Student> s = studentRepository.findById(studentId);
		if (s.isEmpty()) {
			throw new ResourceNotFoundException("Student does not exist with the given ID " + studentId);
		}
		Student student = s.get();
		log.debug("Stduent viewed : ID {}, {}", student.getStudentId(), student.getEmail());
		log.info("Stduent viewed successfully");
		return student;
	}

	/**
	 * Used to update student
	 * 
	 * @param studentDto
	 * @throws ResourceNotFoundException
	 * @return
	 */
	@Override
	public StudentDto updateStudent(StudentDto studentDto) throws ResourceNotFoundException {
		log.info("Updating Student");
		Student student = this.dtoToStudent(studentDto);
		Optional<Student> s = studentRepository.findById(student.getStudentId());
		if (s.isEmpty()) {
			throw new ResourceNotFoundException("Student does not exist with the given ID " + student.getStudentId());
		}
		Student studentToBeUpdated = s.get();
		if (student.getFirstName() != null && !student.getFirstName().equals(""))
			studentToBeUpdated.setFirstName(student.getFirstName());
		if (student.getLastName() != null && !student.getLastName().equals(""))
			studentToBeUpdated.setLastName(student.getLastName());
		if (student.getMobile() != null && !student.getMobile().equals(""))
			studentToBeUpdated.setMobile(student.getMobile());
		if (student.getEmail() != null && !student.getEmail().equals(""))
			studentToBeUpdated.setEmail(student.getEmail());
		if (student.getPassword() != null && !student.getPassword().equals(""))
			studentToBeUpdated.setPassword(student.getPassword());
		if (student.getConfirmPassword() != null && !student.getConfirmPassword().equals(""))
			studentToBeUpdated.setConfirmPassword(student.getConfirmPassword());
		if (student.getIsActive() != null)
			studentToBeUpdated.setIsActive(student.getIsActive());
		Student updatedStudent = studentRepository.save(studentToBeUpdated);
		StudentDto updatedStudentDto = studentToDto(updatedStudent);
		log.debug("Student Updated : {}", updatedStudentDto);
		log.info("Student updated successfully");
		return updatedStudentDto;
	}

	/**
	 * Used to delete a student
	 * 
	 * @param studentId
	 * @throws ResourceNotFoundException
	 * @return
	 */
	@Override
	public Integer deleteStudent(Integer studentId) throws ResourceNotFoundException {
		log.info("Deleting Student");
		Optional<Student> s = studentRepository.findById(studentId);
		if (s.isEmpty()) {
			throw new ResourceNotFoundException("Student does not exist with the given ID " + studentId);
		}
		studentRepository.deleteById(studentId);
		log.debug("Student Deleted : {}", studentId);
		log.info("Student deleted successfully");
		return studentId;
	}

	/**
	 * Converts Student to StudentDto object
	 * 
	 * @param student
	 * @return
	 */
	public StudentDto studentToDto(Student student) {
		StudentDto studentDto = modelMapper.map(student, StudentDto.class);
		return studentDto;
	}

	/**
	 * Converts StudentDto to Student object
	 * 
	 * @param studentDto
	 * @return
	 */
	public Student dtoToStudent(StudentDto studentDto) {
		Student student = modelMapper.map(studentDto, Student.class);
		return student;
	}

	/**
	 * Converts LoginCredentialsDto to Student object
	 * 
	 * @param loginCredentialsDto
	 * @return
	 */
	public Student loginDtoToStudent(LoginCredentialsDto loginCredentialsDto) {
		Student student = modelMapper.map(loginCredentialsDto, Student.class);
		return student;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("Loading user by username: {}", username);
		Student user = studentRepository.findByEmail(username);
		if(user == null) {
			throw new ResourceNotFoundException("User not found.");
		}
		return user;
		
//				.orElseThrow(() -> new UsernameNotFoundException("User not found."));
//		return null;
	}

}
