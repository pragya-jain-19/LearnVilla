package com.cts.learnvilla.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.learnvilla.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {

	/**
	 * custom finder method to find course by name
	 * 
	 * @param courseName
	 * @return
	 */
	public Course findByCourseName(String courseName);

	/**
	 * custom finder method to find courses by category ID
	 * 
	 * @param categoryId
	 * @return
	 */
	public List<Course> findByCategory_CategoryId(String categoryId);

	/**
	 * custom finder method to find courses by category name
	 * 
	 * @param categoryName
	 * @return
	 */
	public List<Course> findByCategory_CategoryName(String categoryName);

	/**
	 * custom finder method to find courses similar to name
	 * 
	 * @param name
	 * @return
	 */
	public List<Course> findByCourseNameContaining(String name);

}
