package com.cts.learnvilla.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.learnvilla.model.Section;

@Repository
public interface SectionRepository extends JpaRepository<Section, Integer> {

	/**
	 * custom finder method to find all sections of a course by course ID
	 * 
	 * @param courseId
	 * @return
	 */
	public List<Section> findByCourse_CourseId(String courseId);

	/**
	 * custom finder method to find all sections of a course by course name
	 * 
	 * @param courseName
	 * @return
	 */
	public List<Section> findByCourse_CourseName(String courseName);

}
