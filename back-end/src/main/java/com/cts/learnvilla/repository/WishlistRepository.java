package com.cts.learnvilla.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.learnvilla.model.Wishlist;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {

	/**
	 * custom finder method to find wishlist by student ID
	 * 
	 * @param studentId
	 * @return
	 */
	public Wishlist findByStudent_StudentId(Integer studentId);

}
