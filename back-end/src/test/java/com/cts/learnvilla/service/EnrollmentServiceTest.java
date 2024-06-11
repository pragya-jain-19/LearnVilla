package com.cts.learnvilla.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import com.cts.learnvilla.dto.EnrollmentDto;
import com.cts.learnvilla.exception.ResourceAlreadyExistsException;
import com.cts.learnvilla.exception.ResourceNotFoundException;
import com.cts.learnvilla.model.Category;
import com.cts.learnvilla.model.Course;
import com.cts.learnvilla.model.Enrollment;
import com.cts.learnvilla.model.Student;
import com.cts.learnvilla.repository.CourseRepository;
import com.cts.learnvilla.repository.EnrollmentRepository;
import com.cts.learnvilla.repository.StudentRepository;
import com.cts.learnvilla.service.impl.EnrollmentServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EnrollmentServiceTest {

	private final Student STUDENT = new Student(1, "Pragya", "Jain", "9384757382", "pragya@gmail.com", "Pragya@123", "Pragya@123", null, null, true, "STUDENT");
	private final Course COURSE = new Course("IT1001", "Java Programming", "Java", new Category("IT", "IT", null), 48.0, 4500.0, 0, 0.0, "image", "English", null, null, null, null);
	private final Course COURSE_2 = new Course("IT1002", "Java Programming", "Java", new Category("IT", "IT", null), 48.0, 4500.0, 0, 0.0, "image", "English", null, null, null, null);
	private final Enrollment ENROLLMENT = new Enrollment(1, STUDENT, COURSE, null, null, 0.0, true);
	private final EnrollmentDto ENROLLMENT_DTO = new EnrollmentDto(1, STUDENT.getStudentId(), COURSE_2.getCourseId(), 0.0, true, null);
	private final Enrollment ENROLLMENT_2 = new Enrollment(1, STUDENT, COURSE_2, null, null, 0.0, true);
	private final EnrollmentDto ENROLLMENT_DTO_2 = new EnrollmentDto(1, STUDENT.getStudentId(), COURSE.getCourseId(), 0.0, true, null);
	private final List<Enrollment> ENROLLMENTS = Arrays.asList(ENROLLMENT);
	
	@Mock
	private ModelMapper modelMapper;
	
	@Mock
	private EnrollmentRepository enrollmentRepository;
	
	@Mock
	private StudentRepository studentRepository;
	
	@Mock
	private CourseRepository courseRepository;
	
	@InjectMocks
	private EnrollmentServiceImpl enrollmentService;
	
	@Test
	public void testAddEnrollment_ValidEnrollment() throws ResourceNotFoundException, ResourceAlreadyExistsException {
		when(modelMapper.map(ENROLLMENT_DTO, Enrollment.class)).thenReturn(ENROLLMENT);
		when(modelMapper.map(ENROLLMENT, EnrollmentDto.class)).thenReturn(ENROLLMENT_DTO);
		when(studentRepository.findById(1)).thenReturn(Optional.of(STUDENT));
		when(courseRepository.findById("IT1001")).thenReturn(Optional.of(COURSE));
		when(enrollmentRepository.save(ENROLLMENT)).thenReturn(ENROLLMENT);
		EnrollmentDto result = enrollmentService.addEnrollment(ENROLLMENT_DTO);
		assertNotNull(result);
		assertEquals(ENROLLMENT_DTO.getEnrollmentId(), result.getEnrollmentId());		
	}
	
	@Test
	public void testAddEnrollment_InvalidStudent() throws ResourceNotFoundException, ResourceAlreadyExistsException {
		when(modelMapper.map(ENROLLMENT_DTO, Enrollment.class)).thenReturn(ENROLLMENT);
		when(studentRepository.findById(1)).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
			enrollmentService.addEnrollment(ENROLLMENT_DTO);
		});
		assertEquals("Student ID : 1 doesn't exists", exception.getMessage());
	}
	
	@Test
	public void testAddEnrollment_InvalidCourse() throws ResourceNotFoundException, ResourceAlreadyExistsException {
		when(modelMapper.map(ENROLLMENT_DTO, Enrollment.class)).thenReturn(ENROLLMENT);
		when(studentRepository.findById(1)).thenReturn(Optional.of(STUDENT));
		when(courseRepository.findById("IT1001")).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
			enrollmentService.addEnrollment(ENROLLMENT_DTO);
		});
		assertEquals("Course ID : IT1001 doesn't exists", exception.getMessage());
	}
	
	@Test
	public void testAddEnrollment_InvalidEnrollment() throws ResourceNotFoundException, ResourceAlreadyExistsException {
		when(modelMapper.map(ENROLLMENT_DTO, Enrollment.class)).thenReturn(ENROLLMENT);
		when(studentRepository.findById(1)).thenReturn(Optional.of(STUDENT));
		when(courseRepository.findById("IT1001")).thenReturn(Optional.of(COURSE));
		when(enrollmentRepository.findByStudent_StudentIdAndCourse_CourseId(1, "IT1001")).thenReturn(ENROLLMENT);
		Exception exception = assertThrows(ResourceAlreadyExistsException.class, ()->{
			enrollmentService.addEnrollment(ENROLLMENT_DTO);
		});
		assertEquals("Enrollment already exists", exception.getMessage());
	}

	@Test
	public void testUpdateEnrollment_ValidEnrollment() throws ResourceNotFoundException, ResourceAlreadyExistsException {
		when(modelMapper.map(ENROLLMENT_DTO, Enrollment.class)).thenReturn(ENROLLMENT_2);
		when(modelMapper.map(ENROLLMENT_2, EnrollmentDto.class)).thenReturn(ENROLLMENT_DTO_2);
		when(enrollmentRepository.findById(1)).thenReturn(Optional.of(ENROLLMENT));
		when(studentRepository.findById(1)).thenReturn(Optional.of(STUDENT));
		when(courseRepository.findById("IT1002")).thenReturn(Optional.of(COURSE_2));
		when(enrollmentRepository.save(ENROLLMENT)).thenReturn(ENROLLMENT_2);
		EnrollmentDto result = enrollmentService.updateEnrollment(ENROLLMENT_DTO);
		assertNotNull(result);
		assertEquals(ENROLLMENT_DTO.getEnrollmentId(), result.getEnrollmentId());
	}
	
	@Test
	public void testUpdateEnrollment_InvalidEnrollment() throws ResourceNotFoundException, ResourceAlreadyExistsException {
		when(modelMapper.map(ENROLLMENT_DTO, Enrollment.class)).thenReturn(ENROLLMENT);
		when(enrollmentRepository.findById(1)).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
			enrollmentService.updateEnrollment(ENROLLMENT_DTO);
		});
		assertEquals("Enrollment ID 1 doesn't exists", exception.getMessage());
	}
	
	@Test
	public void testUpdateEnrollment_InvalidStudent() throws ResourceNotFoundException, ResourceAlreadyExistsException {
		when(modelMapper.map(ENROLLMENT_DTO, Enrollment.class)).thenReturn(ENROLLMENT);
		when(enrollmentRepository.findById(1)).thenReturn(Optional.of(ENROLLMENT));
		when(studentRepository.findById(1)).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
			enrollmentService.updateEnrollment(ENROLLMENT_DTO);
		});
		assertEquals("Student ID : 1 doesn't exists", exception.getMessage());
	}
	
	@Test
	public void testUpdateEnrollment_InvalidCourse() throws ResourceNotFoundException, ResourceAlreadyExistsException {
		when(modelMapper.map(ENROLLMENT_DTO, Enrollment.class)).thenReturn(ENROLLMENT);
		when(enrollmentRepository.findById(1)).thenReturn(Optional.of(ENROLLMENT));
		when(studentRepository.findById(1)).thenReturn(Optional.of(STUDENT));
		when(courseRepository.findById("IT1001")).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
			enrollmentService.updateEnrollment(ENROLLMENT_DTO);
		});
		assertEquals("Course ID : IT1001 doesn't exists", exception.getMessage());
	}
	
	@Test
	public void testUpdateEnrollment_EnrollmentAlreadyExists() throws ResourceNotFoundException, ResourceAlreadyExistsException {
		when(modelMapper.map(ENROLLMENT_DTO, Enrollment.class)).thenReturn(ENROLLMENT);
		when(enrollmentRepository.findById(1)).thenReturn(Optional.of(ENROLLMENT));
		when(studentRepository.findById(1)).thenReturn(Optional.of(STUDENT));
		when(courseRepository.findById("IT1001")).thenReturn(Optional.of(COURSE));
		Exception exception = assertThrows(ResourceAlreadyExistsException.class, ()->{
			enrollmentService.updateEnrollment(ENROLLMENT_DTO);
		});
		assertEquals("Enrollment already exists", exception.getMessage());
	}

	@Test
	public void testCompletionUpdate_ValidEnrollment() throws ResourceNotFoundException {
		when(modelMapper.map(ENROLLMENT_DTO, Enrollment.class)).thenReturn(ENROLLMENT);
		when(modelMapper.map(ENROLLMENT, EnrollmentDto.class)).thenReturn(ENROLLMENT_DTO);
		when(enrollmentRepository.findById(1)).thenReturn(Optional.of(ENROLLMENT));
		when(studentRepository.findById(1)).thenReturn(Optional.of(STUDENT));
		when(courseRepository.findById("IT1001")).thenReturn(Optional.of(COURSE));
		when(enrollmentRepository.save(ENROLLMENT)).thenReturn(ENROLLMENT);
		EnrollmentDto result = enrollmentService.completionUpdate(ENROLLMENT_DTO);
		assertNotNull(result);
		assertEquals(ENROLLMENT_DTO.getEnrollmentId(), result.getEnrollmentId());
	}
	
	@Test
	public void testCompletionUpdate_InvalidEnrollment() throws ResourceNotFoundException {
		when(modelMapper.map(ENROLLMENT_DTO, Enrollment.class)).thenReturn(ENROLLMENT);
		when(enrollmentRepository.findById(1)).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
			enrollmentService.updateEnrollment(ENROLLMENT_DTO);
		});
		assertEquals("Enrollment ID 1 doesn't exists", exception.getMessage());
	}
	
	@Test
	public void testCompletionUpdate_InvalidStudent() throws ResourceNotFoundException {
		when(modelMapper.map(ENROLLMENT_DTO, Enrollment.class)).thenReturn(ENROLLMENT);
		when(enrollmentRepository.findById(1)).thenReturn(Optional.of(ENROLLMENT));
		when(studentRepository.findById(1)).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
			enrollmentService.updateEnrollment(ENROLLMENT_DTO);
		});
		assertEquals("Student ID : 1 doesn't exists", exception.getMessage());
	}
	
	@Test
	public void testCompletionUpdate_InvalidCourse() throws ResourceNotFoundException {
		when(modelMapper.map(ENROLLMENT_DTO, Enrollment.class)).thenReturn(ENROLLMENT);
		when(enrollmentRepository.findById(1)).thenReturn(Optional.of(ENROLLMENT));
		when(studentRepository.findById(1)).thenReturn(Optional.of(STUDENT));
		when(courseRepository.findById("IT1001")).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
			enrollmentService.updateEnrollment(ENROLLMENT_DTO);
		});
		assertEquals("Course ID : IT1001 doesn't exists", exception.getMessage());
	}
	
	@Test
	public void testViewAllEnrollments_ValidEnrollments() throws ResourceNotFoundException {
		when(enrollmentRepository.findAll()).thenReturn(Arrays.asList(ENROLLMENT));
		when(modelMapper.map(ENROLLMENT, EnrollmentDto.class)).thenReturn(ENROLLMENT_DTO);
		
		List<EnrollmentDto> result = enrollmentService.viewAllEnrollments();
		assertEquals(ENROLLMENT_DTO.getEnrollmentId(), result.get(0).getEnrollmentId());
	}
	
	@Test
	public void testViewAllEnrollments_NoEnrollments() throws ResourceNotFoundException {
		when(enrollmentRepository.findAll()).thenReturn(new ArrayList<>());
		Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
			enrollmentService.viewAllEnrollments();
		});
		assertEquals("No enrollments present", exception.getMessage());
	}
	
	@Test
	public void testViewAllEnrollmentsByStudentId_ValidEnrollment() throws ResourceNotFoundException {
		when(studentRepository.findById(1)).thenReturn(Optional.of(STUDENT));
		when(enrollmentRepository.findByStudent_StudentId(1)).thenReturn(ENROLLMENTS);
		List<EnrollmentDto> result = enrollmentService.viewAllEnrollmentsByStudentId(1);
		assertEquals(ENROLLMENTS.size(), result.size());
	}
	
	@Test
	public void testViewAllEnrollmentsByStudentId_NoEnrollments() throws ResourceNotFoundException {
		when(studentRepository.findById(1)).thenReturn(Optional.of(STUDENT));
		when(enrollmentRepository.findByStudent_StudentId(1)).thenReturn(new ArrayList<>());
		Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
			enrollmentService.viewAllEnrollmentsByStudentId(1);
		});
		assertEquals("Student 1 is not enrolled in any course", exception.getMessage());
	}
	
	@Test
	public void testViewAllEnrollmentsByStudentId_InvalidStudent() throws ResourceNotFoundException {
		when(studentRepository.findById(1)).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
			enrollmentService.viewAllEnrollmentsByStudentId(1);
		});
		assertEquals("Student ID : 1 doesn't exists", exception.getMessage());
	}
	
	@Test
	public void testViewAllEnrollmentsByCourseId_ValidEnrollment() throws ResourceNotFoundException {
		when(courseRepository.findById("IT1001")).thenReturn(Optional.of(COURSE));
		when(enrollmentRepository.findByCourse_CourseId("IT1001")).thenReturn(ENROLLMENTS);
		List<EnrollmentDto> result = enrollmentService.viewAllEnrollmentsByCourseId("IT1001");
		assertEquals(ENROLLMENTS.size(), result.size());
	}
	
	@Test
	public void testViewAllEnrollmentsByCourseId_NoEnrollments() throws ResourceNotFoundException {
		when(courseRepository.findById("IT1001")).thenReturn(Optional.of(COURSE));
		when(enrollmentRepository.findByCourse_CourseId("IT1001")).thenReturn(new ArrayList<>());
		Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
			enrollmentService.viewAllEnrollmentsByCourseId("IT1001");
		});
		assertEquals("No student is enrolled in the course ID IT1001", exception.getMessage());
	}
	
	@Test
	public void testViewAllEnrollmentsByCourseId_InvalidCourse() throws ResourceNotFoundException {
		when(courseRepository.findById("IT1001")).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
			enrollmentService.viewAllEnrollmentsByCourseId("IT1001");
		});
		assertEquals("Course ID : IT1001 doesn't exists", exception.getMessage());
	}

	@Test
	public void testViewEnrollmentById_ValidEnrollment() throws ResourceNotFoundException {
		when(enrollmentRepository.findById(1)).thenReturn(Optional.of(ENROLLMENT));
		when(modelMapper.map(ENROLLMENT, EnrollmentDto.class)).thenReturn(ENROLLMENT_DTO);
		EnrollmentDto result = enrollmentService.viewEnrollmentById(1);
		assertEquals(ENROLLMENT.getEnrollmentId(), result.getEnrollmentId());
	}
	
	@Test
	public void testViewEnrollmentById_InvalidEnrollment() throws ResourceNotFoundException {
		when(enrollmentRepository.findById(1)).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
			enrollmentService.viewEnrollmentById(1);
		});
		assertEquals("Enrollment ID 1 doesn't exists", exception.getMessage());
	}
	
	@Test
	public void testDeleteEnrollment_ValidEnrollment() throws ResourceNotFoundException {
		when(enrollmentRepository.findById(1)).thenReturn(Optional.of(ENROLLMENT));
		doNothing().when(enrollmentRepository).deleteById(1);
		enrollmentService.deleteEnrollment(1);
		verify(enrollmentRepository, times(1)).deleteById(1);	
	}
	
	@Test
	public void testDeleteEnrollment_InvalidEnrollment() throws ResourceNotFoundException {
		when(enrollmentRepository.findById(1)).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
			enrollmentService.deleteEnrollment(1);
		});
		assertEquals("Enrollment ID 1 doesn't exists", exception.getMessage());	
	}
	
}
