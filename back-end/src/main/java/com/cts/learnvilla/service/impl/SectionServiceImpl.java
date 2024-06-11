package com.cts.learnvilla.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.learnvilla.dto.SectionDto;
import com.cts.learnvilla.exception.ResourceNotFoundException;
import com.cts.learnvilla.model.Course;
import com.cts.learnvilla.model.Section;
import com.cts.learnvilla.repository.CourseRepository;
import com.cts.learnvilla.repository.SectionRepository;
import com.cts.learnvilla.service.SectionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SectionServiceImpl implements SectionService {

	@Autowired
	private SectionRepository sectionRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Used to add a section
	 * 
	 * @param sectionDto
	 * @return
	 */
	@Override
	public SectionDto addSection(SectionDto sectionDto) throws ResourceNotFoundException {
		log.info("Adding Section");
		Optional<Course> course = courseRepository.findById(sectionDto.getCourseId());
		if (course.isEmpty()) {
			throw new ResourceNotFoundException("No course associated with the course ID " + sectionDto.getCourseId());
		}
		Section section = dtoToSection(sectionDto);
		sectionRepository.save(section);
		SectionDto sectionDtoAdded = sectionToDto(section);
		;
		log.debug("Section Added : {}", sectionDtoAdded);
		log.info("Section Added successfully");
		return sectionDtoAdded;
	}

	/**
	 * Used to update section
	 * 
	 * @param sectionDto
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@Override
	public SectionDto updateSection(SectionDto sectionDto) throws ResourceNotFoundException {
		log.info("Updating Section");
		Section section = dtoToSection(sectionDto);
		Optional<Section> s = sectionRepository.findById(section.getSectionId());
		if (s.isEmpty()) {
			throw new ResourceNotFoundException("No section present with the section ID : " + section.getSectionId());
		}
		Section sectionToBeUpdated = s.get();
		Optional<Course> c = courseRepository.findById(section.getCourse().getCourseId());
		if (c.isEmpty()) {
			throw new ResourceNotFoundException("No course of course ID " + section.getCourse().getCourseId()
					+ " exists of which you want to update the section");
		}
		sectionToBeUpdated.setCourse(c.get());
		sectionToBeUpdated.setSectionNumber(section.getSectionNumber());
		sectionToBeUpdated.setSectionName(section.getSectionName());
		if (section.getSectionContent() != null && !section.getSectionContent().equals(""))
			sectionToBeUpdated.setSectionContent(section.getSectionContent());
		if (section.getVideoUrl() != null && !section.getVideoUrl().equals(""))
			sectionToBeUpdated.setVideoUrl(section.getVideoUrl());
		sectionToBeUpdated.setUpdatedDate(LocalDateTime.now());
		SectionDto updatedSectionDto = sectionToDto(sectionRepository.save(sectionToBeUpdated));
		log.debug("Section Updated : {}", updatedSectionDto);
		log.info("Section Updated successfully");
		return updatedSectionDto;
	}

	/**
	 * Used to view all sections
	 * 
	 * @return
	 * @throws ResourceNotFoundException
	 */
	public List<SectionDto> viewAllSections() throws ResourceNotFoundException {
		log.info("Viewing All Sections");
		List<Section> sections = sectionRepository.findAll();
		if(sections.size() == 0) {
			throw new ResourceNotFoundException("No sections present");
		}
		List<SectionDto> sectionDtos = new ArrayList<>();
		log.info("Sections viewed");
		for(Section s : sections) {
			SectionDto sectionDto = sectionToDto(s);
			log.debug("{}", sectionDto);
			sectionDtos.add(sectionDto);
		}
		log.info("Viewed All Sections");
		return sectionDtos;
	}

	/**
	 * Used to view all sections of given course ID
	 * 
	 * @param courseId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@Override
	public List<Section> viewAllSectionsByCourseId(String courseId) throws ResourceNotFoundException {
		log.info("Viewing All Sections of Course ID {}", courseId);
		Optional<Course> course = courseRepository.findById(courseId);
		if (course.isEmpty()) {
			throw new ResourceNotFoundException("Course ID " + courseId + " doesn't exists");
		}
		List<Section> sections = sectionRepository.findByCourse_CourseId(courseId);
		if (sections.size() == 0) {
			throw new ResourceNotFoundException("No sections present in the course having ID : " + courseId);
		}
		log.debug("Sections present in course {} : ", courseId);
		sections.forEach(s -> log.debug("{}. {} ", s.getSectionNumber(), s.getSectionName()));
		log.info("Sections of Course {} viewed successfully", courseId);
		return sections;
	}

	/**
	 * Used to view all sections of given course name
	 * 
	 * @param courseName
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@Override
	public List<Section> viewAllSectionsByCourseName(String courseName) throws ResourceNotFoundException {
		log.info("Viewing All Sections of Course : {}", courseName);
		Course course = courseRepository.findByCourseName(courseName);
		if (course == null) {
			throw new ResourceNotFoundException("Course name " + courseName + " doesn't exists");
		}
		List<Section> sections = sectionRepository.findByCourse_CourseName(courseName);
		if (sections.size() == 0) {
			throw new ResourceNotFoundException("No sections present in the course : " + courseName);
		}
		log.debug("Sections present in course {} : ", courseName);
		sections.forEach(s -> log.debug("{}. {} ", s.getSectionNumber(), s.getSectionName()));
		log.info("Sections of Course {} viewed successfully", courseName);
		return sections;
	}

	/**
	 * Used to view a section by section ID
	 * 
	 * @param sectionId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@Override
	public SectionDto viewSectionById(Integer sectionId) throws ResourceNotFoundException {
		log.info("Viewing section by ID {}", sectionId);
		Optional<Section> s = sectionRepository.findById(sectionId);
		if (s.isEmpty()) {
			throw new ResourceNotFoundException("No section present with the section ID : " + sectionId);
		}
		Section section = s.get();
		SectionDto sectionDto = sectionToDto(section);
		log.debug("Section viewed {}", sectionDto);
		log.info("Section {} viewed successfully", sectionId);
		return sectionDto;
	}

	/**
	 * Used to delete a section of a particular course by section number
	 * 
	 * @param courseId
	 * @param sectionNumber
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@Override
	public Integer deleteSectionByCourseIdAndSectionNumber(String courseId, Integer sectionNumber)
			throws ResourceNotFoundException {
		log.info("Deleting Section {} of Course {}", sectionNumber, courseId);
		Optional<Course> course = courseRepository.findById(courseId);
		if (course.isEmpty()) {
			throw new ResourceNotFoundException("Course ID " + courseId + " doesn't exists");
		}
		Integer sectionId = -1;
		List<Section> sections = sectionRepository.findByCourse_CourseId(courseId);
		for (Section s : sections) {
			if (s.getSectionNumber() == sectionNumber) {
				sectionId = s.getSectionId();
			}
		}
		if (sectionId == -1) {
			throw new ResourceNotFoundException(
					"No Section present with the section number " + sectionNumber + " in course ID " + courseId);
		}
		sectionRepository.deleteById(sectionId);
		log.debug("Section Number {} Deleted of Course {}", sectionNumber, courseId);
		log.info("Section Deleted successfully");
		return sectionNumber;
	}
	
	/**
	 * Used to delete a section
	 * 
	 * @param sectionId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@Override
	public String deleteSection(Integer sectionId) throws ResourceNotFoundException {
		log.info("Deleting section {}", sectionId);
		Optional<Section> s = sectionRepository.findById(sectionId);
		if (s.isEmpty()) {
			throw new ResourceNotFoundException("No section present with the section ID : " + sectionId);
		}
		sectionRepository.deleteById(sectionId);
		log.info("Section deleted successfully!");
		return "Section deleted " + sectionId;
	}

	/**
	 * Converts Section to SectionDto object
	 * 
	 * @param section
	 * @return
	 */
	public SectionDto sectionToDto(Section section) {
		return this.modelMapper.map(section, SectionDto.class);
	}

	/**
	 * Converts SectionDto to Section object
	 * 
	 * @param sectionDto
	 * @return
	 */
	public Section dtoToSection(SectionDto sectionDto) {
		return this.modelMapper.map(sectionDto, Section.class);
	}

}
