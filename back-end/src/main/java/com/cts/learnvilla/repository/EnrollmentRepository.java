package com.cts.learnvilla.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.learnvilla.model.Enrollment;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {

	/**
	 * custom finder method to find enrollments by student ID
	 * 
	 * @param studentId
	 * @return
	 */
	public List<Enrollment> findByStudent_StudentId(Integer studentId);

	/**
	 * custom finder method to find enrollments by course ID
	 * 
	 * @param courseId
	 * @return
	 */
	public List<Enrollment> findByCourse_CourseId(String courseId);

	/**
	 * custom finder method to find enrollment by student ID and course ID
	 * 
	 * @param studentId
	 * @param courseId
	 * @return
	 */
	public Enrollment findByStudent_StudentIdAndCourse_CourseId(Integer studentId, String courseId);

}
