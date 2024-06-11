package com.cts.learnvilla.service;

import java.util.List;

import com.cts.learnvilla.dto.CourseDto;
import com.cts.learnvilla.exception.ResourceNotFoundException;
import com.cts.learnvilla.model.Course;

public interface CourseService {

	/**
	 * Used to add a course
	 * 
	 * @param courseDto
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public CourseDto addCourse(CourseDto courseDto) throws ResourceNotFoundException;

	/**
	 * Used to update course
	 * 
	 * @param courseDto
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public CourseDto updateCourse(CourseDto courseDto) throws ResourceNotFoundException;

	/**
	 * Used to view course by ID
	 * 
	 * @param courseId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public Course viewCourseById(String courseId) throws ResourceNotFoundException;

	/**
	 * Used to view course by name
	 * 
	 * @param courseName
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public Course viewCourseByName(String courseName) throws ResourceNotFoundException;

	/**
	 * Used to view all courses
	 * 
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public List<Course> viewAllCourses() throws ResourceNotFoundException;

	/**
	 * Used to view all courses of given category ID
	 * 
	 * @param categoryId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public List<Course> viewCoursesByCategoryId(String categoryId) throws ResourceNotFoundException;

	/**
	 * Used to delete a course
	 * 
	 * @param courseId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public String deleteCourse(String courseId) throws ResourceNotFoundException;

	/**
	 * Used to find courses similar to characters entered by user
	 * 
	 * @param name
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public List<Course> viewCoursesByNameLike(String name) throws ResourceNotFoundException;

}
