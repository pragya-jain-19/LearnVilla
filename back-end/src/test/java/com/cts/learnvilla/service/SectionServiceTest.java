package com.cts.learnvilla.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.cts.learnvilla.dto.SectionDto;
import com.cts.learnvilla.exception.ResourceNotFoundException;
import com.cts.learnvilla.model.Category;
import com.cts.learnvilla.model.Course;
import com.cts.learnvilla.model.Section;
import com.cts.learnvilla.repository.CategoryRepository;
import com.cts.learnvilla.repository.CourseRepository;
import com.cts.learnvilla.repository.SectionRepository;
import com.cts.learnvilla.service.impl.SectionServiceImpl;

@ExtendWith(MockitoExtension.class)
public class SectionServiceTest {

	private final Category CATEGORY = new Category("IT", "IT", null);
	private final Course COURSE = new Course("IT1001", "Java Programming", "Java", CATEGORY, 48.0, 4500.0, 0, 0.0, "image", "English", null, null, null, null); 
	
	private final SectionDto SECTION_DTO_1 = new SectionDto(1, "IT1001", 1, "Java Basics", "Java Basics", "java video"); 
	private final Section SECTION_1 = new Section(1, COURSE, 1, "Java Basics", "Java Basics", "java video", null, null);
	private final List<Section> SECTIONS = Arrays.asList(SECTION_1);
	
	@Mock
	private ModelMapper modelMapper;
	
	@Mock
	private SectionRepository sectionRepository;
	
	@Mock
	private CourseRepository courseRepository;
	
	@Mock
	private CategoryRepository categoryRepository;
	
	@InjectMocks
	private SectionServiceImpl sectionService;
	
	@Test
	public void testAddSection_ValidSection() throws ResourceNotFoundException {
		when(courseRepository.findById("IT1001")).thenReturn(Optional.of(COURSE));
		when(sectionRepository.save(SECTION_1)).thenReturn(SECTION_1);
		when(modelMapper.map(SECTION_DTO_1, Section.class)).thenReturn(SECTION_1);
		when(modelMapper.map(SECTION_1, SectionDto.class)).thenReturn(SECTION_DTO_1);
		SectionDto result = sectionService.addSection(SECTION_DTO_1);
		assertNotNull(result);
		assertEquals(SECTION_DTO_1.getSectionId(), result.getSectionId());		
	}
	
	@Test
	public void testAddSection_InvalidCourse() throws ResourceNotFoundException {
		when(courseRepository.findById("IT1001")).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
			sectionService.addSection(SECTION_DTO_1);
		});
		assertEquals("No course associated with the course ID IT1001", exception.getMessage());	
	}

	@Test
	public void testViewSectionById_ValidSection() throws ResourceNotFoundException {
		when(sectionRepository.findById(1)).thenReturn(Optional.of(SECTION_1));
		when(modelMapper.map(SECTION_1, SectionDto.class)).thenReturn(SECTION_DTO_1);
		SectionDto result = sectionService.viewSectionById(1);
		assertEquals(SECTION_1.getSectionId(), result.getSectionId());
	}
	
	@Test
	public void testViewSectionById_InvalidSection() throws ResourceNotFoundException {
		when(sectionRepository.findById(1)).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
			sectionService.viewSectionById(1);
		});
		assertEquals("No section present with the section ID : 1", exception.getMessage());
	}
	 
	@Test
	public void testUpdateSection_ValidSection() throws ResourceNotFoundException {
		when(modelMapper.map(SECTION_DTO_1, Section.class)).thenReturn(SECTION_1);
		when(modelMapper.map(SECTION_1, SectionDto.class)).thenReturn(SECTION_DTO_1);
		when(sectionRepository.findById(1)).thenReturn(Optional.of(SECTION_1));
		when(courseRepository.findById("IT1001")).thenReturn(Optional.of(COURSE));
		when(sectionRepository.save(SECTION_1)).thenReturn(SECTION_1);
		SectionDto result = sectionService.updateSection(SECTION_DTO_1);
		assertNotNull(result);
 		assertEquals(SECTION_DTO_1.getSectionId(), result.getSectionId());
//		when(courseRepository.save(COURSE_1)).thenReturn(COURSE_1);
//		CourseDto result = courseService.updateCourse(COURSE_DTO_1);
// 		assertNotNull(result);
// 		assertEquals(COURSE_DTO_1.getCourseId(), result.getCourseId());
	}
	
	@Test
	public void testUpdateSection_InvalidSection() throws ResourceNotFoundException {
		when(modelMapper.map(SECTION_DTO_1, Section.class)).thenReturn(SECTION_1);
		when(sectionRepository.findById(1)).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
			sectionService.updateSection(SECTION_DTO_1);
		});
		assertEquals("No section present with the section ID : 1", exception.getMessage());
	}
	
	@Test
	public void testUpdateSection_InvalidCourse() throws ResourceNotFoundException {
		when(modelMapper.map(SECTION_DTO_1, Section.class)).thenReturn(SECTION_1);
		when(sectionRepository.findById(1)).thenReturn(Optional.of(SECTION_1));
		when(courseRepository.findById("IT1001")).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
			sectionService.updateSection(SECTION_DTO_1);
		});
		assertEquals("No course of course ID IT1001 exists of which you want to update the section", exception.getMessage());
	}
	
	@Test
	public void testViewAllSectionsByCourseId_ValidCourseId() throws ResourceNotFoundException {
		when(courseRepository.findById("IT1001")).thenReturn(Optional.of(COURSE));
		when(sectionRepository.findByCourse_CourseId("IT1001")).thenReturn(SECTIONS);
		List<Section> result = sectionService.viewAllSectionsByCourseId("IT1001");
		assertNotNull(result);
 		assertEquals(SECTIONS.size(), result.size());
	}
	
	@Test
	public void testViewAllSectionsByCourseId_InvalidCourseId() throws ResourceNotFoundException {
		when(courseRepository.findById("IT1001")).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
			sectionService.viewAllSectionsByCourseId("IT1001");
		});
		assertEquals("Course ID IT1001 doesn't exists", exception.getMessage());
	}
	
	@Test
	public void testViewAllSectionsByCourseId_NoSections() throws ResourceNotFoundException {
		when(courseRepository.findById("IT1001")).thenReturn(Optional.of(COURSE));
		when(sectionRepository.findByCourse_CourseId("IT1001")).thenReturn(new ArrayList<>());
		Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
			sectionService.viewAllSectionsByCourseId("IT1001");
		});
		assertEquals("No sections present in the course having ID : IT1001", exception.getMessage());
	}
	
	@Test
	public void testViewAllSectionsByCourseName_ValidCourseName() throws ResourceNotFoundException {
		when(courseRepository.findByCourseName("Java Programming")).thenReturn(COURSE);
		when(sectionRepository.findByCourse_CourseName("Java Programming")).thenReturn(SECTIONS);
		List<Section> result = sectionService.viewAllSectionsByCourseName("Java Programming");
		assertNotNull(result);
 		assertEquals(SECTIONS.size(), result.size());
	}
	
	@Test
	public void testViewAllSectionsByCourseName_InvalidCourseName() throws ResourceNotFoundException {
		when(courseRepository.findByCourseName("Java Programming")).thenReturn(null);
		Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
			sectionService.viewAllSectionsByCourseName("Java Programming");
		});
		assertEquals("Course name Java Programming doesn't exists", exception.getMessage());
	}
	
	@Test
	public void testViewAllSectionsByCourseName_NoSections() throws ResourceNotFoundException {
		when(courseRepository.findByCourseName("Java Programming")).thenReturn(COURSE);
		when(sectionRepository.findByCourse_CourseName("Java Programming")).thenReturn(new ArrayList<>());
		Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
			sectionService.viewAllSectionsByCourseName("Java Programming");
		});
		assertEquals("No sections present in the course : Java Programming", exception.getMessage());
	}

	@Test
	public void testDeleteSectionByCourseIdAndSectionNumber_ValidCourse() throws ResourceNotFoundException {
		when(courseRepository.findById("IT1001")).thenReturn(Optional.of(COURSE));
		when(sectionRepository.findByCourse_CourseId("IT1001")).thenReturn(SECTIONS);
		doNothing().when(sectionRepository).deleteById(1);
		sectionService.deleteSectionByCourseIdAndSectionNumber("IT1001", 1);
		verify(sectionRepository, times(1)).deleteById(1);	
	}
	
	@Test
	public void testDeleteSectionByCourseIdAndSectionNumber_InvalidCourseId() throws ResourceNotFoundException {
		when(courseRepository.findById("IT1001")).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
			sectionService.deleteSectionByCourseIdAndSectionNumber("IT1001", 1);
		});
		assertEquals("Course ID IT1001 doesn't exists", exception.getMessage());	
	}
	
	@Test
	public void testDeleteSectionByCourseIdAndSectionNumber_InvalidSectionId() throws ResourceNotFoundException {
		when(courseRepository.findById("IT1001")).thenReturn(Optional.of(COURSE));
		when(sectionRepository.findByCourse_CourseId("IT1001")).thenReturn(new ArrayList<>());
		Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
			sectionService.deleteSectionByCourseIdAndSectionNumber("IT1001", 1);
		});
		assertEquals("No Section present with the section number 1 in course ID IT1001", exception.getMessage());	
	}
	
}
