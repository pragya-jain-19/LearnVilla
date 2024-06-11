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

import com.cts.learnvilla.dto.CourseDto;
import com.cts.learnvilla.model.Course;
import com.cts.learnvilla.service.CourseService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/learnvilla/api/courses")
public class CourseController {

	@Autowired
	private CourseService courseService;

	/**
	 * Add course
	 * 
	 * @param courseDto
	 * @return
	 */
	@PostMapping("/add-course")
	public ResponseEntity<CourseDto> addCourse(@Valid @RequestBody CourseDto courseDto) {
		CourseDto addedCourseDto = courseService.addCourse(courseDto);
		return new ResponseEntity<>(addedCourseDto, HttpStatus.CREATED);
	}

	/**
	 * View all courses
	 * 
	 * @return
	 */
	@GetMapping("/view-all-courses")
	public ResponseEntity<List<Course>> viewAllCourses() {
		List<Course> courses = courseService.viewAllCourses();
		return new ResponseEntity<>(courses, HttpStatus.OK);
	}

	/**
	 * View specific course by ID
	 * 
	 * @param courseId
	 * @return
	 */
	@GetMapping("/view-course/course-id/{courseId}")
	public ResponseEntity<Course> viewCourseById(@PathVariable String courseId) {
		Course course = courseService.viewCourseById(courseId);
		return new ResponseEntity<>(course, HttpStatus.OK);
	}

	/**
	 * View specific course by course name
	 * 
	 * @param courseName
	 * @return
	 */
	@GetMapping("/view-course/course-name/{courseName}")
	public ResponseEntity<Course> viewCourseByName(@PathVariable String courseName) {
		Course course = courseService.viewCourseByName(courseName);
		return new ResponseEntity<>(course, HttpStatus.OK);
	}

	/**
	 * View all courses of a specific category by category ID
	 * 
	 * @param categoryId
	 * @return
	 */
	@GetMapping("/view-all-courses/category-id/{categoryId}")
	public ResponseEntity<List<Course>> viewCoursesByCategoryId(@PathVariable String categoryId) {
		List<Course> courses = courseService.viewCoursesByCategoryId(categoryId);
		return new ResponseEntity<>(courses, HttpStatus.OK);
	}

	/**
	 * Update course
	 * 
	 * @param courseDto
	 * @return
	 */
	@PutMapping("/update-course")
	public ResponseEntity<CourseDto> updateCourse(@Valid @RequestBody CourseDto courseDto) {
		CourseDto updatedCourseDto = courseService.updateCourse(courseDto);
		return new ResponseEntity<>(updatedCourseDto, HttpStatus.OK);
	}

	/**
	 * Delete course
	 * 
	 * @param courseId
	 * @return
	 */
	@DeleteMapping("/delete-course/{courseId}")
	public ResponseEntity<String> deleteCourse(@PathVariable String courseId) {
		String courseDeleted = courseService.deleteCourse(courseId);
		return new ResponseEntity<>("Course deleted with ID : " + courseDeleted, HttpStatus.OK);
	}

	/**
	 * View courses containing specific characters or pattern
	 * 
	 * @param name
	 * @return
	 */
	@GetMapping("/view-all-courses/course-name-like/{name}")
	public ResponseEntity<List<Course>> viewCoursesByNameLike(@PathVariable String name) {
		List<Course> courses = courseService.viewCoursesByNameLike(name);
		return new ResponseEntity<>(courses, HttpStatus.OK);
	}
}
