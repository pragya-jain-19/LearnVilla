package com.cts.learnvilla.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

import com.cts.learnvilla.dto.LoginCredentialsDto;
import com.cts.learnvilla.dto.StudentDto;
import com.cts.learnvilla.exception.InvalidCredentials;
import com.cts.learnvilla.exception.ResourceAlreadyExistsException;
import com.cts.learnvilla.exception.ResourceNotFoundException;
import com.cts.learnvilla.model.Student;
import com.cts.learnvilla.repository.StudentRepository;
import com.cts.learnvilla.service.impl.StudentServiceImpl;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

	private final StudentDto STUDENT_DTO = new StudentDto(1, "Pragya", "Jain", "8743958309", "pragya@gmail.com", "Pragya@12", "Pragya@12", true, "STUDENT");
	private final Student STUDENT = new Student(1, "Pragya", "Jain", "8743958309", "pragya@gmail.com", "Pragya@12", "Pragya@12", null, null, true, "STUDENT");
	private final LoginCredentialsDto LOGIN_CREDENTIALS_DTO = new LoginCredentialsDto(1, "pragya@gmail.com", "Pragya@12", "Pragya@12", "STUDENT");
	private final List<Student> STUDENTS = Arrays.asList(STUDENT);
	private final Student STUDENT_2 = new Student(1, "Pragya", "Jain", "8743958309", "pragya@gmail.com", "Pragya@123", "Pragya@12", null, null, true, "STUDENT");
	private final StudentDto STUDENT_DTO_2 = new StudentDto(1, "Pragya", "Jain", "8743958309", "pragya@gmail.com", "Pragya@123", "Pragya@12", true, "STUDENT");
	private final LoginCredentialsDto LOGIN_CREDENTIALS_DTO_2 = new LoginCredentialsDto(1, "pragya@gmail.com", "Pragya@123", "Pragya@12", "STUDENT");
	
	@Mock
	private ModelMapper modelMapper;
	
	@Mock
	private StudentRepository studentRepository;
	
	@InjectMocks
	private StudentServiceImpl studentService;
	
	@Test
	public void testRegisterStudent_ValidRegistration() throws ResourceAlreadyExistsException, InvalidCredentials {
		when(modelMapper.map(STUDENT_DTO, Student.class)).thenReturn(STUDENT);
		when(modelMapper.map(STUDENT, StudentDto.class)).thenReturn(STUDENT_DTO);
		when(studentRepository.findByEmail("pragya@gmail.com")).thenReturn(null);
		when(studentRepository.findByMobile("8743958309")).thenReturn(null);
		when(studentRepository.save(STUDENT)).thenReturn(STUDENT);
		StudentDto result = studentService.registerStudent(STUDENT_DTO);
		assertEquals(STUDENT_DTO, result);
	}
	
	@Test
	public void testRegisterStudent_StudentExists() throws ResourceAlreadyExistsException, InvalidCredentials {
		when(modelMapper.map(STUDENT_DTO, Student.class)).thenReturn(STUDENT);
		when(studentRepository.findByEmail("pragya@gmail.com")).thenReturn(STUDENT);
		Exception exception = assertThrows(ResourceAlreadyExistsException.class, () -> {
			studentService.registerStudent(STUDENT_DTO);
		});
		assertEquals("Student already exists. Please login using your credentials.", exception.getMessage());
	}
	
	@Test
	public void testRegisterStudent_MobileExists() throws ResourceAlreadyExistsException, InvalidCredentials {
		when(modelMapper.map(STUDENT_DTO, Student.class)).thenReturn(STUDENT);
		when(studentRepository.findByEmail("pragya@gmail.com")).thenReturn(null);
		when(studentRepository.findByMobile("8743958309")).thenReturn(STUDENT);
		Exception exception = assertThrows(ResourceAlreadyExistsException.class, () -> {
			studentService.registerStudent(STUDENT_DTO);
		});
		assertEquals("Mobile number already exists. Please register uisng different number.", exception.getMessage());
	}
	
	@Test
	public void testRegisterStudent_InvalidCredentials() throws ResourceAlreadyExistsException, InvalidCredentials {
		when(modelMapper.map(STUDENT_DTO_2, Student.class)).thenReturn(STUDENT_2);
		when(studentRepository.findByEmail("pragya@gmail.com")).thenReturn(null);
		when(studentRepository.findByMobile("8743958309")).thenReturn(null);
		Exception exception = assertThrows(InvalidCredentials.class, () -> {
			studentService.registerStudent(STUDENT_DTO_2);
		});
		assertEquals("Password should match with confirm password", exception.getMessage());
	}
	
	@Test
	public void testLoginStudent_ValidLogin() throws InvalidCredentials, ResourceNotFoundException {
		when(modelMapper.map(LOGIN_CREDENTIALS_DTO, Student.class)).thenReturn(STUDENT);
		when(studentRepository.findByEmail("pragya@gmail.com")).thenReturn(STUDENT);
		Boolean result = studentService.loginStudent(LOGIN_CREDENTIALS_DTO);
		assertTrue(result);
	}
	
	@Test
	public void testLoginStudent_PasswordMismatch() throws InvalidCredentials, ResourceNotFoundException {
		when(modelMapper.map(LOGIN_CREDENTIALS_DTO_2, Student.class)).thenReturn(STUDENT_2);
		Exception exception = assertThrows(InvalidCredentials.class, () -> {
			studentService.loginStudent(LOGIN_CREDENTIALS_DTO_2);
		});
		assertEquals("Password field should match with confirm password!", exception.getMessage());
	}
	
	@Test
	public void testLoginStudent_InvalidStudent() throws InvalidCredentials, ResourceNotFoundException {
		when(modelMapper.map(LOGIN_CREDENTIALS_DTO, Student.class)).thenReturn(STUDENT);
		when(studentRepository.findByEmail("pragya@gmail.com")).thenReturn(null);
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			studentService.loginStudent(LOGIN_CREDENTIALS_DTO);
		});
		assertEquals("Student credentials not found", exception.getMessage());
	}
	
	@Test
	public void testLoginStudent_InvalidCredentials() throws InvalidCredentials, ResourceNotFoundException {
		when(modelMapper.map(LOGIN_CREDENTIALS_DTO, Student.class)).thenReturn(STUDENT);
		when(studentRepository.findByEmail("pragya@gmail.com")).thenReturn(STUDENT_2);
		Exception exception = assertThrows(InvalidCredentials.class, () -> {
			studentService.loginStudent(LOGIN_CREDENTIALS_DTO);
		});
		assertEquals("Invalid Credentials!", exception.getMessage());
	}
	
	@Test
	public void testViewAllStudents_ValidStudents() throws ResourceNotFoundException {
		when(studentRepository.findAll()).thenReturn(STUDENTS);
		List<Student> result = studentService.viewAllStudents();
		assertEquals(STUDENTS.size(), result.size());
	}
	
	@Test
	public void testViewAllStudents_NoStudent() throws ResourceNotFoundException {
		when(studentRepository.findAll()).thenReturn(new ArrayList<>());
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			studentService.viewAllStudents();
		});
		assertEquals("No students present", exception.getMessage());
	}
	
	@Test
	public void testViewStudentById_ValidStudent() throws ResourceNotFoundException {
		when(studentRepository.findById(1)).thenReturn(Optional.of(STUDENT));
		Student result = studentService.viewStudentById(1);
		assertEquals(STUDENT, result);
	}
	
	@Test
	public void testViewStudentById_NoStudent() throws ResourceNotFoundException {
		when(studentRepository.findById(1)).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			studentService.viewStudentById(1);
		});
		assertEquals("Student does not exist with the given ID 1", exception.getMessage());
	}
	
	@Test
	public void testUpdateStudent_ValidStudent() throws ResourceNotFoundException {
		when(modelMapper.map(STUDENT_DTO, Student.class)).thenReturn(STUDENT);
		when(modelMapper.map(STUDENT, StudentDto.class)).thenReturn(STUDENT_DTO);
		when(studentRepository.findById(1)).thenReturn(Optional.of(STUDENT));
		when(studentRepository.save(STUDENT)).thenReturn(STUDENT);
		StudentDto result = studentService.updateStudent(STUDENT_DTO);
		assertNotNull(result);
		assertEquals(STUDENT_DTO, result);
	}
	
	@Test
	public void testUpdateStudent_NoStudent() throws ResourceNotFoundException {
		when(modelMapper.map(STUDENT_DTO, Student.class)).thenReturn(STUDENT);
		when(studentRepository.findById(1)).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			studentService.updateStudent(STUDENT_DTO);
		});
		assertEquals("Student does not exist with the given ID 1", exception.getMessage());
	}
	
	@Test
	public void testDeleteStudent_ValidStudent() throws ResourceNotFoundException {
		when(studentRepository.findById(1)).thenReturn(Optional.of(STUDENT));
		doNothing().when(studentRepository).deleteById(1);
		studentService.deleteStudent(1);
		verify(studentRepository, times(1)).deleteById(1);
	}
	
	@Test
	public void testDeleteStudent_NoStudent() throws ResourceNotFoundException {
		when(studentRepository.findById(1)).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			studentService.deleteStudent(1);
		});
		assertEquals("Student does not exist with the given ID 1", exception.getMessage());
	}
	
}
