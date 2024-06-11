package com.cts.learnvilla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.learnvilla.dto.EnrollmentDto;
import com.cts.learnvilla.service.EnrollmentService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/learnvilla/api/enrollments")
public class EnrollmentController {

	@Autowired
	private EnrollmentService enrollmentService;

	/**
	 * Add enrollment
	 * 
	 * @param enrollmentDto
	 * @return
	 */
	@PostMapping("/add-enrollment")
	public ResponseEntity<EnrollmentDto> addEnrollment(@Valid @RequestBody EnrollmentDto enrollmentDto) {
		EnrollmentDto addedEnrollmentDto = enrollmentService.addEnrollment(enrollmentDto);
		return new ResponseEntity<>(addedEnrollmentDto, HttpStatus.CREATED);
	}

	/**
	 * View all enrollments
	 * 
	 * @return
	 */
	@GetMapping("/view-all-enrollments")
	public ResponseEntity<List<EnrollmentDto>> viewAllEnrollments() {
		List<EnrollmentDto> enrollmentsDto = enrollmentService.viewAllEnrollments();
		return new ResponseEntity<>(enrollmentsDto, HttpStatus.OK);
	}

	/**
	 * Update enrollment
	 * 
	 * @param enrollmentDto
	 * @return
	 */
	@PutMapping("/update-enrollment")
	public ResponseEntity<EnrollmentDto> updateEnrollment(@Valid @RequestBody EnrollmentDto enrollmentDto) {
		EnrollmentDto updatedEnrollmentDto = enrollmentService.updateEnrollment(enrollmentDto);
		return new ResponseEntity<>(updatedEnrollmentDto, HttpStatus.OK);
	}

	/**
	 * Update completion status
	 * 
	 * @param enrollmentDto
	 * @return
	 */
	@PutMapping("/update-completion-status")
	public ResponseEntity<EnrollmentDto> completionUpdate(@Valid @RequestBody EnrollmentDto enrollmentDto) {
		EnrollmentDto updatedEnrollmentDto = enrollmentService.completionUpdate(enrollmentDto);
		return new ResponseEntity<>(updatedEnrollmentDto, HttpStatus.OK);
	}

	/**
	 * View all enrollments of a specific student by student ID
	 * 
	 * @param studentId
	 * @return
	 */
	@GetMapping("/view-all-enrollments/student/{studentId}")
	public ResponseEntity<List<EnrollmentDto>> viewAllEnrollmentsByStudentId(@PathVariable Integer studentId) {
		List<EnrollmentDto> enrollmentsDto = enrollmentService.viewAllEnrollmentsByStudentId(studentId);
		return new ResponseEntity<>(enrollmentsDto, HttpStatus.OK);
	}

	/**
	 * View all enrollments in a specific course by course ID
	 * 
	 * @param studentId
	 * @return
	 */
	@GetMapping("/view-all-enrollments/course/{courseId}")
	public ResponseEntity<List<EnrollmentDto>> viewAllEnrollmentsByCourseId(@PathVariable String courseId) {
		List<EnrollmentDto> enrollmentsDto = enrollmentService.viewAllEnrollmentsByCourseId(courseId);
		return new ResponseEntity<>(enrollmentsDto, HttpStatus.OK);
	}

	/**
	 * View enrollment by enrollment ID
	 * 
	 * @param enrollmentId
	 * @return
	 */
	@GetMapping("/view-enrollment/{enrollmentId}")
	public ResponseEntity<EnrollmentDto> viewEnrollmentById(@PathVariable Integer enrollmentId) {
		EnrollmentDto enrollmentDto = enrollmentService.viewEnrollmentById(enrollmentId);
		return new ResponseEntity<>(enrollmentDto, HttpStatus.OK);
	}

	/**
	 * Delete enrollment by enrollment ID
	 * 
	 * @param enrollmentId
	 * @return
	 */
	@DeleteMapping("/delete-enrollment/{enrollmentId}")
	public ResponseEntity<String> deleteEnrollment(@PathVariable Integer enrollmentId) {
		Integer enrollmentIdDeleted = enrollmentService.deleteEnrollment(enrollmentId);
		return new ResponseEntity<>("Enrollment ID " + enrollmentIdDeleted + " deleted", HttpStatus.OK);
	}

}
