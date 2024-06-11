package com.cts.learnvilla.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.learnvilla.dto.CourseDto;
import com.cts.learnvilla.exception.ResourceAlreadyExistsException;
import com.cts.learnvilla.exception.ResourceNotFoundException;
import com.cts.learnvilla.model.Category;
import com.cts.learnvilla.model.Course;
import com.cts.learnvilla.repository.CategoryRepository;
import com.cts.learnvilla.repository.CourseRepository;
import com.cts.learnvilla.service.CourseService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Used to add a course
	 * 
	 * @param courseDto
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@Override
	public CourseDto addCourse(CourseDto courseDto) throws ResourceNotFoundException {
		log.info("Adding Course");
		Optional<Category> c = categoryRepository.findById(courseDto.getCategoryId());
		if (c.isEmpty()) {
			throw new ResourceNotFoundException("Category ID " + courseDto.getCategoryId() + " doesn't exists");
		}
		Optional<Course> co = courseRepository.findById(courseDto.getCourseId());
		if (!co.isEmpty()) {
			throw new ResourceAlreadyExistsException("Course ID " + courseDto.getCourseId() + " already exists");
		}
		Course course = dtoToCourse(courseDto);
		courseRepository.save(course);
		CourseDto addedCourseDto = courseToDto(course);
		log.debug("Course Added : {}", addedCourseDto);
		log.info("Course Added successfully");
		return addedCourseDto;
	}

	/**
	 * Used to view course by ID
	 * 
	 * @param courseId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@Override
	public Course viewCourseById(String courseId) throws ResourceNotFoundException {
		log.info("Viewing Course by ID {}", courseId);
		Optional<Course> c = courseRepository.findById(courseId);
		if (c.isEmpty()) {
			throw new ResourceNotFoundException("No course present with the course ID : " + courseId);
		}
		Course course = c.get();
		log.debug("Course viewed : {} {}", course.getCourseId(), course.getCourseName());
		log.info("Course Viewed successfully");
		return course;
	}

	/**
	 * Used to view course by name
	 * 
	 * @param courseName
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@Override
	public Course viewCourseByName(String courseName) throws ResourceNotFoundException {
		log.info("Viewing Course by Name");
		Course course = courseRepository.findByCourseName(courseName);
		if (course == null) {
			throw new ResourceNotFoundException("No course present with the course name : " + courseName);
		}
		log.debug("Course viewed : {} {}", course.getCourseId(), course.getCourseName());
		log.info("Course Viewed successfully");
		return course;
	}

	/**
	 * Used to view all courses
	 * 
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@Override
	public List<Course> viewAllCourses() throws ResourceNotFoundException {
		log.info("Viewing All Courses");
		List<Course> courses = courseRepository.findAll();
		if (courses.size() == 0) {
			throw new ResourceNotFoundException("No courses present");
		}
		log.debug("Courses present : ");
		courses.forEach(c -> log.debug(c.getCourseName()));
		log.info("Courses Viewed successfully");
		return courses;
	}

	/**
	 * Used to view all courses of given category ID
	 * 
	 * @param categoryId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@Override
	public List<Course> viewCoursesByCategoryId(String categoryId) throws ResourceNotFoundException {
		log.info("Viewing Courses by Category ID {}", categoryId);
		Optional<Category> c = categoryRepository.findById(categoryId);
		if (c.isEmpty()) {
			throw new ResourceNotFoundException("Category ID " + categoryId + " doesn't exists");
		}
		List<Course> courses = courseRepository.findByCategory_CategoryId(categoryId);
		if (courses.size() == 0) {
			throw new ResourceNotFoundException("No courses present in the category : " + categoryId);
		}
		log.debug("Courses present in category {} : ", categoryId);
		courses.forEach(course -> log.debug(course.getCourseName()));
		log.info("Courses of Category ID {} Viewed successfully", categoryId);
		return courses;
	}

	/**
	 * Used to update course
	 * 
	 * @param courseDto
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@Override
	public CourseDto updateCourse(CourseDto courseDto) throws ResourceNotFoundException {
		log.info("Updating Course");
		Course course = dtoToCourse(courseDto);
		Optional<Course> c = courseRepository.findById(course.getCourseId());
		if (c.isEmpty()) {
			throw new ResourceNotFoundException("No course present with the course ID : " + course.getCourseId());
		}
		Course courseToBeUpdated = c.get();
		Optional<Category> cat = categoryRepository.findById(course.getCategory().getCategoryId());
		if (cat.isEmpty()) {
			throw new ResourceNotFoundException(
					"Category ID " + course.getCategory().getCategoryId() + " doesn't exists");
		}
		Category category = cat.get();
		if (courseDto.getCourseName() != null && !courseDto.getCourseName().equals(""))
			courseToBeUpdated.setCourseName(course.getCourseName());
		if (courseDto.getDescription() != null && !courseDto.getDescription().equals(""))
			courseToBeUpdated.setDescription(course.getDescription());
		courseToBeUpdated.setCategory(category);
		if (courseDto.getDuration() != null && courseDto.getDuration() != 0.0)
			courseToBeUpdated.setDuration(course.getDuration());
		if (courseDto.getPrice() != null && courseDto.getPrice() != 0.0)
			courseToBeUpdated.setPrice(course.getPrice());
		if (courseDto.getStudentsEnrolled() != null && courseDto.getStudentsEnrolled() != 0)
			courseToBeUpdated.setStudentsEnrolled(course.getStudentsEnrolled());
		if (courseDto.getRating() != null && courseDto.getRating() != 0.0)
			courseToBeUpdated.setRating(course.getRating());
		if (courseDto.getCourseImage() != null && !courseDto.getCourseImage().equals(""))
			courseToBeUpdated.setCourseImage(course.getCourseImage());
		if (courseDto.getLanguage() != null && !courseDto.getLanguage().equals(""))
			courseToBeUpdated.setLanguage(course.getLanguage());
		courseToBeUpdated.setUpdatedDate(LocalDateTime.now());
		CourseDto updatedCourseDto = courseToDto(courseRepository.save(courseToBeUpdated));
		log.debug("Course Updated : {}", updatedCourseDto);
		log.info("Course Updated successfully");
		return updatedCourseDto;
	}

	/**
	 * Used to delete a course
	 * 
	 * @param courseId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@Override
	public String deleteCourse(String courseId) throws ResourceNotFoundException {
		log.info("Deleting Course");
		Optional<Course> course = courseRepository.findById(courseId);
		if (course.isEmpty()) {
			throw new ResourceNotFoundException("No course present with the course ID : " + courseId);
		}
		courseRepository.deleteById(courseId);
		log.debug("Course Deleted : {}", courseId.toUpperCase());
		log.info("Course Deleted successfully");
		return courseId.toUpperCase();
	}

	// Test cases are pending
	/**
	 * Used to find courses similar to characters entered by user
	 * 
	 * @param name
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@Override
	public List<Course> viewCoursesByNameLike(String name) throws ResourceNotFoundException {
		log.info("Viewing Courses containing Name : {}", name);
		List<Course> courses = courseRepository.findByCourseNameContaining(name.toLowerCase());
		if (courses.size() == 0) {
			throw new ResourceNotFoundException("No courses present containing : " + name);
		}
		log.debug("Courses viewed with name containing {} : ", name);
		courses.forEach(course -> log.debug(course.getCourseName()));
		log.info("Courses Viewed successfully");
		return courses;
	}

	/**
	 * Converts Course to CourseDto object
	 * 
	 * @param course
	 * @return
	 */
	public CourseDto courseToDto(Course course) {
		return this.modelMapper.map(course, CourseDto.class);
	}

	/**
	 * Converts CourseDto to Course object
	 * 
	 * @param courseDto
	 * @return
	 */
	public Course dtoToCourse(CourseDto courseDto) {
		return this.modelMapper.map(courseDto, Course.class);
	}

}
