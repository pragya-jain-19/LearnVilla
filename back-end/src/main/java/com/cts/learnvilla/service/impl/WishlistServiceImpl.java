package com.cts.learnvilla.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.learnvilla.dto.WishlistDto;
import com.cts.learnvilla.exception.ResourceAlreadyExistsException;
import com.cts.learnvilla.exception.ResourceNotFoundException;
import com.cts.learnvilla.model.Course;
import com.cts.learnvilla.model.Student;
import com.cts.learnvilla.model.Wishlist;
import com.cts.learnvilla.repository.CourseRepository;
import com.cts.learnvilla.repository.StudentRepository;
import com.cts.learnvilla.repository.WishlistRepository;
import com.cts.learnvilla.service.WishlistService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WishlistServiceImpl implements WishlistService {

	@Autowired
	private WishlistRepository wishlistRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Method to calculate price when adding course into wishlist
	 * 
	 * @param price
	 * @param course
	 * @return
	 */
	public Double calculatePriceWhenAddingCourse(Double price, Course course) {
		log.debug("Calculating price of courses");
		Double updatedPrice = price;
		updatedPrice += course.getPrice();
		log.debug("Calculated price of courses : {}", updatedPrice);
		return updatedPrice;
	}

	/**
	 * Method to calculate price when course is deleted from wishlist
	 * 
	 * @param price
	 * @param course
	 * @return
	 */
	public Double calculatePriceWhenDeletingCourse(Double price, Course course) {
		log.debug("Calculating price of courses :");
		Double updatedPrice = price;
		updatedPrice -= course.getPrice();
		log.debug("Calculated price of courses : {}", updatedPrice);
		return updatedPrice;
	}

	/**
	 * Used to insert a course into wishlist
	 * 
	 * @param courseId
	 * @param studentId
	 * @return
	 * @throws ResourceNotFoundException
	 * @throws ResourceAlreadyExistsException
	 */
	@Override
	public WishlistDto insertCourseIntoWishlist(String courseId, Integer studentId)
			throws ResourceNotFoundException, ResourceAlreadyExistsException {
		log.info("Inserting course {} into Wishlist of Student ID {}", courseId, studentId);
		Optional<Course> course = courseRepository.findById(courseId);
		if (course.isEmpty()) {
			throw new ResourceNotFoundException("No course available with the course ID " + courseId);
		}
		Optional<Student> student = studentRepository.findById(studentId);
		if (student.isEmpty()) {
			throw new ResourceNotFoundException("Student ID " + studentId + " doesn't exists");
		}
		Wishlist retrievedWishlist = wishlistRepository.findByStudent_StudentId(studentId);
		Wishlist wishlist = null;
		Wishlist updatedWishlist = null;

		/**
		 * 
		 * New student with no cart -> creating cart
		 * 
		 */
		if (retrievedWishlist == null) {
			log.debug("No wishlist associated with the student. Creating new Wishlist.");
			wishlist = new Wishlist();
			wishlist.setStudent(student.get());
			List<Course> courses = new ArrayList<>();
			courses.add(course.get());
			log.debug("Adding course to the wishlist");
			wishlist.setCourses(courses);
			wishlist.setPrice(calculatePriceWhenAddingCourse(0.0, course.get()));
			updatedWishlist = wishlistRepository.save(wishlist);
			log.debug("Wishlist created and course added");
		}

		/**
		 * 
		 * Old student having a cart -> student already exists with a cart
		 * 
		 */
		else {
			log.debug("Wishlist of student {} already exists", studentId);
			wishlist = retrievedWishlist;

			// check if course already exists in the wishlist
			if (wishlist.getCourses().contains(course.get())) {
				throw new ResourceAlreadyExistsException("Course already exists in wishlist");
			}

			// else update the price in the wishlist and add the course to wishlist
			else {
				log.debug("Adding course into the wishlist");
				wishlist.addCourseToWishlist(course.get());
				wishlist.setPrice(calculatePriceWhenAddingCourse(wishlist.getPrice(), course.get()));
				updatedWishlist = wishlistRepository.save(wishlist);
				log.debug("Course added to the wishlist");
			}
		}
		WishlistDto updatedWishlistDto = wishlistToDto(updatedWishlist);
		List<String> courses = new ArrayList<>();
		for (Course temp : updatedWishlist.getCourses()) {
			courses.add(temp.getCourseId());
		}
		updatedWishlistDto.setCourseIds(courses);
		log.debug("Updated Wishlist {} of student {}", updatedWishlistDto.getWishlistId(),
				updatedWishlistDto.getStudentId());
		updatedWishlist.getCourses().forEach(c -> log.debug("{}. {}", c.getCourseId(), c.getCourseName()));
		log.info("Inserted course into wishlist successfully");
		return updatedWishlistDto;
	}

	/**
	 * Used to delete a course from wishlist
	 * 
	 * @param courseId
	 * @param studentId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@Override
	public WishlistDto deleteCourseFromWishlist(String courseId, Integer studentId) throws ResourceNotFoundException {
		log.info("Deleting course {} from wishlist of Student ID {}", courseId, studentId);
		Optional<Student> student = studentRepository.findById(studentId);
		if (student.isEmpty()) {
			throw new ResourceNotFoundException("Student ID " + studentId + " doesn't exists");
		}
		Optional<Course> course = courseRepository.findById(courseId);
		if (course.isEmpty()) {
			throw new ResourceNotFoundException("No course available with the course ID " + courseId);
		}
		Wishlist wishlist = wishlistRepository.findByStudent_StudentId(studentId);
		Wishlist updatedWishlist = null;

		// wishlist is not there
		if (wishlist == null) {
			throw new ResourceNotFoundException("No wishlist present corresponding to student ID : " + studentId);
		}

		// Course not exists in wishlist
		if (!wishlist.getCourses().contains(course.get())) {
			throw new ResourceNotFoundException("Course not present in the wishlist");
		}

		// Course present in wishlist -> delete it after updating the price
		log.debug("Course is present in the student's wishlist");
		wishlist.deleteCourseFromWishlist(course.get());
		wishlist.setPrice(calculatePriceWhenDeletingCourse(wishlist.getPrice(), course.get()));
		updatedWishlist = wishlistRepository.save(wishlist);
		log.debug("Course deleted from wishlist");

		// If cart gets empty, just delete the cart completely
		if (updatedWishlist.getCourses().size() == 0) {
			log.debug("Wishlist is empty. Deleting the wishlist.");
			wishlistRepository.deleteById(updatedWishlist.getWishlistId());
			log.debug("Wishlist deleted successfully");
			return null;
		}
		WishlistDto updatedWishlistDto = (updatedWishlist != null) ? wishlistToDto(updatedWishlist) : null;
		List<String> courses = new ArrayList<>();
		for (Course temp : updatedWishlist.getCourses()) {
			courses.add(temp.getCourseId());
		}
		updatedWishlistDto.setCourseIds(courses);
		log.info("Course deleted from wishlist successfully");
		return updatedWishlistDto;
	}

	/**
	 * Used to view wishlist of a specific student by given student ID
	 * 
	 * @param studentId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@Override
	public Wishlist viewWishlistByStudentId(Integer studentId) throws ResourceNotFoundException {
		log.info("Viewing wishlist of student {}", studentId);
		Optional<Student> student = studentRepository.findById(studentId);
		if (student.isEmpty()) {
			throw new ResourceNotFoundException("Student ID " + studentId + " doesn't exists");
		}
		Wishlist wishlist = wishlistRepository.findByStudent_StudentId(studentId);
		if (wishlist == null) {
			throw new ResourceNotFoundException("No wishlist corresponding to student ID " + studentId);
		}
		log.debug("Student's Wishlist :");
		wishlist.getCourses().forEach(c -> log.debug("{}. {}", c.getCourseId(), c.getCourseName()));
		log.info("Student's wishlist viewed successfully");
		return wishlist;
	}

	/**
	 * Used to delete wishlist of a specific student by given student ID
	 * 
	 * @param studentId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@Override
	public Integer deleteWishlistByStudentId(Integer studentId) throws ResourceNotFoundException {
		log.info("Deleting wishlist of student {}", studentId);
		Optional<Student> student = studentRepository.findById(studentId);
		if (student.isEmpty()) {
			throw new ResourceNotFoundException("Student ID " + studentId + " doesn't exists");
		}
		Wishlist wishlist = wishlistRepository.findByStudent_StudentId(studentId);
		if (wishlist == null) {
			throw new ResourceNotFoundException("No wishlist corresponding to student ID " + studentId);
		}
		Integer wishlistId = wishlist.getWishlistId();
		wishlistRepository.deleteById(wishlistId);
		log.debug("Wishlist of student's ID {} deleted", studentId);
		log.info("Student's wishlist deleted successfully");
		return wishlistId;
	}

	/**
	 * Converts Wishlist to WishlistDto object
	 * 
	 * @param wishlist
	 * @return
	 */
	public WishlistDto wishlistToDto(Wishlist wishlist) {
		return this.modelMapper.map(wishlist, WishlistDto.class);
	}

}
