package com.cts.learnvilla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.learnvilla.dto.StudentDto;
import com.cts.learnvilla.model.Student;
import com.cts.learnvilla.service.StudentService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/learnvilla/api/students")
public class StudentController {

	@Autowired
	private StudentService studentService;

	/**
	 * View all students
	 * 
	 * @return
	 */
	@GetMapping("/view-all-students")
	public ResponseEntity<List<Student>> viewAllStudents() {
		List<Student> students = studentService.viewAllStudents();
		return new ResponseEntity<>(students, HttpStatus.OK);
	}

	/**
	 * View student by ID
	 * 
	 * @param studentId
	 * @return
	 */
	@GetMapping("/view-student/{studentId}")
	public ResponseEntity<Student> viewStudentById(@PathVariable Integer studentId) {
		Student student = studentService.viewStudentById(studentId);
		return new ResponseEntity<>(student, HttpStatus.OK);
	}

	/**
	 * Update student
	 * 
	 * @param studentDto
	 * @return
	 */
	@PutMapping("/update-student")
	public ResponseEntity<StudentDto> updateStudent(@Valid @RequestBody StudentDto studentDto) {
		StudentDto updatedStudentDto = studentService.updateStudent(studentDto);
		return new ResponseEntity<>(updatedStudentDto, HttpStatus.OK);
	}

	/**
	 * Delete student
	 * 
	 * @param studentId
	 * @return
	 */
	@DeleteMapping("/delete-student/{studentId}")
	public ResponseEntity<String> deleteStudent(@PathVariable Integer studentId) {
		Integer studentDeleted = studentService.deleteStudent(studentId);
		return new ResponseEntity<>("Student deleted with the ID : " + studentDeleted, HttpStatus.OK);
	}

}
