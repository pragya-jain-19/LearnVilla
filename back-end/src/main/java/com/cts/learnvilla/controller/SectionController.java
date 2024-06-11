package com.cts.learnvilla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.learnvilla.dto.SectionDto;
import com.cts.learnvilla.model.Section;
import com.cts.learnvilla.service.SectionService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/learnvilla/api/sections")
public class SectionController {

	@Autowired
	private SectionService sectionService;

	/**
	 * Add section
	 * 
	 * @param sectionDto
	 * @return
	 */
	@PostMapping("/add-section")
	public ResponseEntity<SectionDto> addSection(@Valid @RequestBody SectionDto sectionDto) {
		SectionDto sectionDtoAdded = sectionService.addSection(sectionDto);
		return new ResponseEntity<>(sectionDtoAdded, HttpStatus.CREATED);
	}

	/**
	 * View all sections
	 * 
	 * @return
	 */
	@GetMapping("/view-all-sections")
	public ResponseEntity<List<SectionDto>> viewAllSections() {
		List<SectionDto> sections = sectionService.viewAllSections();
		return new ResponseEntity<>(sections, HttpStatus.OK);
	}

	/**
	 * View all sections of a specific course by course ID
	 * 
	 * @param courseId
	 * @return
	 */
	@GetMapping("/view-all-sections/course-id/{courseId}")
	public ResponseEntity<List<Section>> viewAllSectionsByCourseId(@PathVariable String courseId) {
		List<Section> sections = sectionService.viewAllSectionsByCourseId(courseId);
		return new ResponseEntity<>(sections, HttpStatus.OK);
	}

	/**
	 * View all sections of a specific course by course name
	 * 
	 * @param courseName
	 * @return
	 */
	@GetMapping("/view-all-sections/course-name/{courseName}")
	public ResponseEntity<List<Section>> viewAllSectionsByCourseName(@PathVariable String courseName) {
		List<Section> sections = sectionService.viewAllSectionsByCourseName(courseName);
		return new ResponseEntity<>(sections, HttpStatus.OK);
	}

	/**
	 * View section by section ID
	 * 
	 * @param sectionId
	 * @return
	 */
	@GetMapping("/view-section/{sectionId}")
	public ResponseEntity<SectionDto> viewSectionById(@PathVariable Integer sectionId) {
		SectionDto sectionDto = sectionService.viewSectionById(sectionId);
		return new ResponseEntity<>(sectionDto, HttpStatus.OK);
	}

	/**
	 * Update section
	 * 
	 * @param sectionDto
	 * @return
	 */
	@PutMapping("/update-section")
	public ResponseEntity<SectionDto> updateSection(@Valid @RequestBody SectionDto sectionDto) {
		SectionDto updatedSectionDto = sectionService.updateSection(sectionDto);
		return new ResponseEntity<>(updatedSectionDto, HttpStatus.OK);
	}

	/**
	 * Delete a section of a specific course and specific number
	 * 
	 * @param courseId
	 * @param sectionNumber
	 * @return
	 */
	@DeleteMapping("/delete-section/course-id/{courseId}/{sectionNumber}")
	public ResponseEntity<String> deleteSectionByCourseIdAndSectionNumber(@PathVariable String courseId,
			@PathVariable Integer sectionNumber) {
		Integer sectionDeleted = sectionService.deleteSectionByCourseIdAndSectionNumber(courseId, sectionNumber);
		return new ResponseEntity<>("Section Number " + sectionDeleted + " deleted of Course ID : " + courseId,
				HttpStatus.OK);
	}

	/**
	 * Delete section by ID
	 * 
	 * @param sectionId
	 * @return
	 */
	@DeleteMapping("/delete-section/{section-id}")
	public ResponseEntity<String> deleteSection(@PathVariable Integer sectionId) {
		String sectionDeleted = sectionService.deleteSection(sectionId);
		return new ResponseEntity<>(sectionDeleted, HttpStatus.OK);
	}

}
