package com.cts.learnvilla.service;

import java.util.List;

import com.cts.learnvilla.dto.SectionDto;
import com.cts.learnvilla.exception.ResourceNotFoundException;
import com.cts.learnvilla.model.Section;

public interface SectionService {

	/**
	 * Used to add a section
	 * 
	 * @param sectionDto
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public SectionDto addSection(SectionDto sectionDto) throws ResourceNotFoundException;

	/**
	 * Used to update section
	 * 
	 * @param sectionDto
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public SectionDto updateSection(SectionDto sectionDto) throws ResourceNotFoundException;

	/**
	 * Used to view all sections
	 * 
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public List<SectionDto> viewAllSections() throws ResourceNotFoundException;

	/**
	 * Used to view all sections of given course ID
	 * 
	 * @param courseId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public List<Section> viewAllSectionsByCourseId(String courseId) throws ResourceNotFoundException;

	/**
	 * Used to view all sections of given course name
	 * 
	 * @param courseName
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public List<Section> viewAllSectionsByCourseName(String courseName) throws ResourceNotFoundException;

	/**
	 * Used to view a section by section ID
	 * 
	 * @param sectionId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public SectionDto viewSectionById(Integer sectionId) throws ResourceNotFoundException;

	/**
	 * Used to delete a section of a particular course by section number
	 * 
	 * @param courseId
	 * @param sectionNumber
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public Integer deleteSectionByCourseIdAndSectionNumber(String courseId, Integer sectionNumber)
			throws ResourceNotFoundException;

	/**
	 * Used to delete a section
	 * 
	 * @param sectionId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public String deleteSection(Integer sectionId) throws ResourceNotFoundException;

}
