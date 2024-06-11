package com.cts.learnvilla.service;

import com.cts.learnvilla.dto.WishlistDto;
import com.cts.learnvilla.exception.ResourceAlreadyExistsException;
import com.cts.learnvilla.exception.ResourceNotFoundException;
import com.cts.learnvilla.model.Wishlist;

public interface WishlistService {

	/**
	 * Used to insert a course into wishlist
	 * 
	 * @param courseId
	 * @param studentId
	 * @return
	 * @throws ResourceNotFoundException
	 * @throws ResourceAlreadyExistsException
	 */
	public WishlistDto insertCourseIntoWishlist(String courseId, Integer studentId)
			throws ResourceNotFoundException, ResourceAlreadyExistsException;

	/**
	 * Used to delete a course from wishlist
	 * 
	 * @param courseId
	 * @param studentId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public WishlistDto deleteCourseFromWishlist(String courseId, Integer studentId) throws ResourceNotFoundException;

	/**
	 * Used to view wishlist of a specific student by given student ID
	 * 
	 * @param studentId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public Wishlist viewWishlistByStudentId(Integer studentId) throws ResourceNotFoundException;

	/**
	 * Used to delete wishlist of a specific student by given student ID
	 * 
	 * @param studentId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public Integer deleteWishlistByStudentId(Integer studentId) throws ResourceNotFoundException;

}
