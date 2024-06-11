package com.cts.learnvilla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.learnvilla.dto.WishlistDto;
import com.cts.learnvilla.model.Wishlist;
import com.cts.learnvilla.service.WishlistService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/learnvilla/api/wishlist")
public class WishlistController {

	@Autowired
	private WishlistService wishlistService;

	/**
	 * Used to insert course into wishlist
	 * 
	 * @param courseId
	 * @param studentId
	 * @return
	 */
	@PostMapping("/insert-course/{studentId}/{courseId}")
	public ResponseEntity<WishlistDto> insertCourseIntoWishlist(@PathVariable String courseId,
			@PathVariable Integer studentId) {
		WishlistDto wishlistDto = wishlistService.insertCourseIntoWishlist(courseId, studentId);
		return new ResponseEntity<>(wishlistDto, HttpStatus.CREATED);
	}

	/**
	 * Used to delete course from wishlist
	 * 
	 * @param courseId
	 * @param studentId
	 * @return
	 */
	@DeleteMapping("/delete-course/{studentId}/{courseId}")
	public ResponseEntity<WishlistDto> deleteCourseFromWishlist(@PathVariable String courseId,
			@PathVariable Integer studentId) {
		WishlistDto wishlistDto = wishlistService.deleteCourseFromWishlist(courseId, studentId);
		return new ResponseEntity<>(wishlistDto, HttpStatus.OK);
	}

	/**
	 * Used to view wishlist by student ID
	 * 
	 * @param studentId
	 * @return
	 */
	@GetMapping("/view-wishlist/{studentId}")
	public ResponseEntity<Wishlist> viewWishlistByStudentId(@PathVariable Integer studentId) {
		Wishlist wishlist = wishlistService.viewWishlistByStudentId(studentId);
		return new ResponseEntity<>(wishlist, HttpStatus.OK);
	}

	/**
	 * Used to delete wishlist by student ID
	 * 
	 * @param studentId
	 * @return
	 */
	@DeleteMapping("/delete-wishlist/{studentId}")
	public ResponseEntity<String> deleteWishlistByStudentId(@PathVariable Integer studentId) {
		Integer wishlistId = wishlistService.deleteWishlistByStudentId(studentId);
		return new ResponseEntity<>("Wishlist ID " + wishlistId + " deleted of student's ID " + studentId,
				HttpStatus.OK);
	}

}
