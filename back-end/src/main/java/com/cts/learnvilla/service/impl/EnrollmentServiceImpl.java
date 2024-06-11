package com.cts.learnvilla.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cts.learnvilla.dto.EnrollmentDto;
import com.cts.learnvilla.exception.ResourceAlreadyExistsException;
import com.cts.learnvilla.exception.ResourceNotFoundException;
import com.cts.learnvilla.model.Course;
import com.cts.learnvilla.model.Enrollment;
import com.cts.learnvilla.model.Student;
import com.cts.learnvilla.repository.CourseRepository;
import com.cts.learnvilla.repository.EnrollmentRepository;
import com.cts.learnvilla.repository.StudentRepository;
import com.cts.learnvilla.service.EnrollmentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class EnrollmentServiceImpl implements EnrollmentService {

	@Autowired
	private EnrollmentRepository enrollmentRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Used to add enrollment
	 * 
	 * @param enrollmentDto
	 * @throws ResourceNotFoundException
	 * @throws ResourceAlreadyExistsException
	 * @return
	 */
	@Override
	public EnrollmentDto addEnrollment(EnrollmentDto enrollmentDto)
			throws ResourceNotFoundException, ResourceAlreadyExistsException {
		log.info("Adding Enrollment");
		Enrollment enrollment = dtoToEnrollment(enrollmentDto);
		Optional<Student> student = studentRepository.findById(enrollment.getStudent().getStudentId());
		if (student.isEmpty()) {
			throw new ResourceNotFoundException(
					"Student ID : " + enrollment.getStudent().getStudentId() + " doesn't exists");
		}
		Optional<Course> course = courseRepository.findById(enrollment.getCourse().getCourseId());
		if (course.isEmpty()) {
			throw new ResourceNotFoundException(
					"Course ID : " + enrollment.getCourse().getCourseId() + " doesn't exists");
		}
		Enrollment e = enrollmentRepository.findByStudent_StudentIdAndCourse_CourseId(
				enrollment.getStudent().getStudentId(), enrollment.getCourse().getCourseId());
		if (e != null) {
			throw new ResourceAlreadyExistsException("Enrollment already exists");
		}
		Enrollment enrollmentAdded = enrollmentRepository.save(enrollment);
		EnrollmentDto enrollmentDtoAdded = enrollmentToDto(enrollmentAdded);
		log.debug("Enrollment Added : {}", enrollmentDtoAdded);
		log.info("Enrollment Added successfully");
		return enrollmentDtoAdded;
	}

	/**
	 * Used to update enrollment
	 * 
	 * @param enrollmentDto
	 * @return
	 * @throws ResourceNotFoundException
	 * @throws ResourceAlreadyExistsException
	 */
	@Override
	public EnrollmentDto updateEnrollment(EnrollmentDto enrollmentDto)
			throws ResourceNotFoundException, ResourceAlreadyExistsException {
		log.info("Updating Enrollment");
		Enrollment enrollment = dtoToEnrollment(enrollmentDto);
		Optional<Enrollment> e = enrollmentRepository.findById(enrollment.getEnrollmentId());
		if (e.isEmpty()) {
			throw new ResourceNotFoundException("Enrollment ID " + enrollment.getEnrollmentId() + " doesn't exists");
		}
		Enrollment enrollmentToBeUpdated = e.get();
		Optional<Student> s = studentRepository.findById(enrollment.getStudent().getStudentId());
		if (s.isEmpty()) {
			throw new ResourceNotFoundException(
					"Student ID : " + enrollment.getStudent().getStudentId() + " doesn't exists");
		}
		Student student = s.get();
		Optional<Course> c = courseRepository.findById(enrollment.getCourse().getCourseId());
		if (c.isEmpty()) {
			throw new ResourceNotFoundException(
					"Course ID : " + enrollment.getCourse().getCourseId() + " doesn't exists");
		}
		Course course = c.get();
		if (enrollmentToBeUpdated.getStudent().getStudentId() == enrollment.getStudent().getStudentId()
				&& enrollmentToBeUpdated.getCourse().getCourseId().equals(enrollment.getCourse().getCourseId())) {
			throw new ResourceAlreadyExistsException("Enrollment already exists");
		}
		enrollmentToBeUpdated.setStudent(student);
		enrollmentToBeUpdated.setCourse(course);
		Enrollment updatedEnrollment = enrollmentRepository.save(enrollmentToBeUpdated);
		EnrollmentDto updatedEnrollmentDto = enrollmentToDto(updatedEnrollment);
		log.debug("Enrollment Updated : {}", updatedEnrollmentDto);
		log.info("Enrollment Updated successfully");
		return updatedEnrollmentDto;
	}

	/**
	 * Used to update completion status of course
	 * 
	 * @param enrollmentDto
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@Override
	public EnrollmentDto completionUpdate(EnrollmentDto enrollmentDto) throws ResourceNotFoundException {
		log.info("Updating Completion Status of Enrollment");
		Enrollment enrollment = dtoToEnrollment(enrollmentDto);
		Optional<Enrollment> e = enrollmentRepository.findById(enrollment.getEnrollmentId());
		if (e.isEmpty()) {
			throw new ResourceNotFoundException("Enrollment ID " + enrollment.getEnrollmentId() + " doesn't exists");
		}
		Enrollment enrollmentToBeUpdated = e.get();
		Optional<Student> s = studentRepository.findById(enrollment.getStudent().getStudentId());
		if (s.isEmpty()) {
			throw new ResourceNotFoundException(
					"Student ID : " + enrollment.getStudent().getStudentId() + " doesn't exists");
		}
		Optional<Course> c = courseRepository.findById(enrollment.getCourse().getCourseId());
		if (c.isEmpty()) {
			throw new ResourceNotFoundException(
					"Course ID : " + enrollment.getCourse().getCourseId() + " doesn't exists");
		}
		enrollmentToBeUpdated.setCompletedDate(LocalDateTime.now());
		enrollmentToBeUpdated.setIsProgressLeft(false);
		enrollmentToBeUpdated.setProgress(100.0);
		Enrollment updatedEnrollment = enrollmentRepository.save(enrollmentToBeUpdated);
		EnrollmentDto updatedEnrollmentDto = enrollmentToDto(updatedEnrollment);
		log.debug("Enrollment Completion Status Updated : {}", updatedEnrollmentDto);
		log.info("Enrollment Completion Status Updated successfully");
		return updatedEnrollmentDto;
	}

	/**
	 * Used to view all enrollments
	 * 
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@Override
	public List<EnrollmentDto> viewAllEnrollments() throws ResourceNotFoundException {
		log.info("Viewing All Enrollments");
		List<Enrollment> enrollments = enrollmentRepository.findAll();
		if (enrollments.size() == 0) {
			throw new ResourceNotFoundException("No enrollments present");
		}
		List<EnrollmentDto> enrollmentsDto = new ArrayList<>();
		for (Enrollment e : enrollments) {
			enrollmentsDto.add(enrollmentToDto(e));
		}
		log.debug("Enrollments present : ");
		enrollmentsDto.forEach(e -> log.debug("Student ID : " + e.getStudentId() + ", Course ID : " + e.getCourseId()));
		log.info("Viewed All Enrollments successfully");
		return enrollmentsDto;
	}

	/**
	 * Used to view all enrollments of a particular student, given studentId
	 * 
	 * @param studentId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@Override
	public List<EnrollmentDto> viewAllEnrollmentsByStudentId(Integer studentId) throws ResourceNotFoundException {
		log.info("Viewing all the enrollments of student {}", studentId);
		Optional<Student> student = studentRepository.findById(studentId);
		if (student.isEmpty()) {
			throw new ResourceNotFoundException("Student ID : " + studentId + " doesn't exists");
		}
		List<Enrollment> enrollments = enrollmentRepository.findByStudent_StudentId(studentId);
		if (enrollments.size() == 0) {
			throw new ResourceNotFoundException("Student " + studentId + " is not enrolled in any course");
		}
		List<EnrollmentDto> enrollmentsDto = new ArrayList<>();
		for (Enrollment e : enrollments) {
			enrollmentsDto.add(enrollmentToDto(e));
		}
//		log.debug("Enrollments of student ID {} :", studentId);
//		enrollmentsDto.forEach(e -> log.debug("Course ID : {}", e.getCourseId()));
		log.info("Viewed all the enrollments of student {} successfully", studentId);
		return enrollmentsDto;
	}

	/**
	 * Used to view all enrollments in a particular course, given courseId
	 * 
	 * @param courseId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@Override
	public List<EnrollmentDto> viewAllEnrollmentsByCourseId(String courseId) throws ResourceNotFoundException {
		log.info("Viewing all the enrollments of course {}", courseId);
		Optional<Course> course = courseRepository.findById(courseId);
		if (course.isEmpty()) {
			throw new ResourceNotFoundException("Course ID : " + courseId + " doesn't exists");
		}
		List<Enrollment> enrollments = enrollmentRepository.findByCourse_CourseId(courseId);
		if (enrollments.size() == 0) {
			throw new ResourceNotFoundException("No student is enrolled in the course ID " + courseId);
		}
		List<EnrollmentDto> enrollmentsDto = new ArrayList<>();
		for (Enrollment e : enrollments) {
			enrollmentsDto.add(enrollmentToDto(e));
		}
//		log.debug("Enrollments of course ID {} :", courseId);
//		enrollmentsDto.forEach(e -> log.debug("Student ID : {}", e.getStudentId()));
		log.info("Viewed all the enrollments of course {} successfully", courseId);
		return enrollmentsDto;
	}

	/**
	 * Used to view enrollment by ID
	 * 
	 * @param enrollmentId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@Override
	public EnrollmentDto viewEnrollmentById(Integer enrollmentId) throws ResourceNotFoundException {
		log.info("Viewing Enrollment by ID {}", enrollmentId);
		Optional<Enrollment> e = enrollmentRepository.findById(enrollmentId);
		if (e.isEmpty()) {
			throw new ResourceNotFoundException("Enrollment ID " + enrollmentId + " doesn't exists");
		}
		Enrollment enrollment = e.get();
		EnrollmentDto enrollmentDto = enrollmentToDto(enrollment);
		log.debug("Enrollment Viewed {} : ", enrollmentDto);
		log.info("Viewed Enrollment successfully");
		return enrollmentDto;
	}

	/**
	 * Used to delete enrollment
	 * 
	 * @param enrollmentId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@Override
	public Integer deleteEnrollment(Integer enrollmentId) throws ResourceNotFoundException {
		log.info("Deleting Enrollment");
		Optional<Enrollment> e = enrollmentRepository.findById(enrollmentId);
		if (e.isEmpty()) {
			throw new ResourceNotFoundException("Enrollment ID " + enrollmentId + " doesn't exists");
		}
		enrollmentRepository.deleteById(enrollmentId);
		log.debug("Enrollment {} Deleted", enrollmentId);
		log.info("Enrollment Deleted successfully");
		return enrollmentId;
	}

	/**
	 * Converts Enrollment to EnrollmentDto object
	 * 
	 * @param enrollment
	 * @return
	 */
	public EnrollmentDto enrollmentToDto(Enrollment enrollment) {
		return this.modelMapper.map(enrollment, EnrollmentDto.class);
	}

	/**
	 * Converts EnrollmentDto to Enrollment object
	 * 
	 * @param enrollmentDto
	 * @return
	 */
	public Enrollment dtoToEnrollment(EnrollmentDto enrollmentDto) {
		return this.modelMapper.map(enrollmentDto, Enrollment.class);
	}

}
