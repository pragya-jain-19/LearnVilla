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

import com.cts.learnvilla.dto.CourseDto;
import com.cts.learnvilla.exception.ResourceNotFoundException;
import com.cts.learnvilla.model.Category;
import com.cts.learnvilla.model.Course;
import com.cts.learnvilla.repository.CategoryRepository;
import com.cts.learnvilla.repository.CourseRepository;
import com.cts.learnvilla.service.impl.CourseServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

	private final Category CATEGORY = new Category("IT", "IT", null);
	private final CourseDto COURSE_DTO_1 = new CourseDto("IT1001", "Java Programming", "Java", "IT", 48.0, 4500.0, 0,
			0.0, "image", "English");
	private final Course COURSE_1 = new Course("IT1001", "Java Programming", "Java", CATEGORY, 48.0, 4500.0, 0, 0.0,
			"image", "English", null, null, null, null),
			COURSE_2 = new Course("IT1002", "Python Programming", "Python", CATEGORY, 48.0, 4500.0, 0, 0.0, "image",
					"English", null, null, null, null);
	private final List<Course> COURSES = Arrays.asList(COURSE_1, COURSE_2);

	@Mock
	private ModelMapper modelMapper;

	@Mock
	private CourseRepository courseRepository;

	@Mock
	private CategoryRepository categoryRepository;

	@InjectMocks
	private CourseServiceImpl courseService;

	@Test
	public void testAddCourse_ValidCourse() {
		when(categoryRepository.findById("IT")).thenReturn(Optional.of(CATEGORY));
		when(courseRepository.findById("IT1001")).thenReturn(Optional.empty());
		when(courseRepository.save(COURSE_1)).thenReturn(COURSE_1);
		when(modelMapper.map(COURSE_DTO_1, Course.class)).thenReturn(COURSE_1);
		when(modelMapper.map(COURSE_1, CourseDto.class)).thenReturn(COURSE_DTO_1);
		CourseDto result = courseService.addCourse(COURSE_DTO_1);
		assertNotNull(result);
		assertEquals(COURSE_DTO_1.getCourseId(), result.getCourseId());
	}
	
	@Test
	public void testAddCourse_InvalidCourse() {
		when(categoryRepository.findById("IT")).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			courseService.addCourse(COURSE_DTO_1);
		});
		assertEquals("Category ID IT doesn't exists", exception.getMessage());
	}

	@Test
	public void testViewCourseById_ValidCourse() throws ResourceNotFoundException {
		when(courseRepository.findById("IT1001")).thenReturn(Optional.of(COURSE_1));
		Course result = courseService.viewCourseById("IT1001");
		assertEquals(COURSE_1.getCourseId(), result.getCourseId());
	}

	@Test
	public void testViewCourseById_InvalidCourse() throws ResourceNotFoundException {
		when(courseRepository.findById("IT1001")).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			courseService.viewCourseById("IT1001");
		});
		assertEquals("No course present with the course ID : IT1001", exception.getMessage());
	}

	@Test
	public void testViewCourseByName_ValidCourse() throws ResourceNotFoundException {
		when(courseRepository.findByCourseName("Java Programming")).thenReturn(COURSE_1);
		Course result = courseService.viewCourseByName("Java Programming");
		assertEquals(COURSE_1.getCourseName(), result.getCourseName());
	}

	@Test
	public void testViewCourseByName_InvalidCourse() throws ResourceNotFoundException {
		when(courseRepository.findByCourseName("Java Programming")).thenReturn(null);
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			courseService.viewCourseByName("Java Programming");
		});
		assertEquals("No course present with the course name : Java Programming", exception.getMessage());
	}

	@Test
	public void testViewAllCourses_ValidCourses() throws ResourceNotFoundException {
		when(courseRepository.findAll()).thenReturn(COURSES);
		List<Course> result = courseService.viewAllCourses();
		assertEquals(COURSES, result);
	}

	@Test
	public void testViewAllCourses_NoCourses() throws ResourceNotFoundException {
		List<Course> courses = new ArrayList<>();
		when(courseRepository.findAll()).thenReturn(courses);
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			courseService.viewAllCourses();
		});
		assertEquals("No courses present", exception.getMessage());
	}

	@Test
	public void testViewCoursesByCategoryId_ValidCategoryId() throws ResourceNotFoundException {
		when(categoryRepository.findById("IT")).thenReturn(Optional.of(CATEGORY));
		when(courseRepository.findByCategory_CategoryId("IT")).thenReturn(COURSES);
		List<Course> courses = courseService.viewCoursesByCategoryId("IT");
		assertEquals(2, courses.size());
	}

	@Test
	public void testViewCoursesByCategoryId_InvalidCategoryId() throws ResourceNotFoundException {
		when(categoryRepository.findById("IT")).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			courseService.viewCoursesByCategoryId("IT");
		});
		assertEquals("Category ID IT doesn't exists", exception.getMessage());
	}

	@Test
	public void testViewCoursesByCategoryId_InvalidCourse() throws ResourceNotFoundException {
		when(categoryRepository.findById("IT")).thenReturn(Optional.of(CATEGORY));
		when(courseRepository.findByCategory_CategoryId("IT")).thenReturn(new ArrayList<>());
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			courseService.viewCoursesByCategoryId("IT");
		});
		assertEquals("No courses present in the category : IT", exception.getMessage());
	}

	@Test
	public void testUpdateCourse_ValidCourse() throws ResourceNotFoundException {
		when(courseRepository.findById("IT1001")).thenReturn(Optional.of(COURSE_1));
		when(categoryRepository.findById("IT")).thenReturn(Optional.of(CATEGORY));
		when(courseRepository.save(COURSE_1)).thenReturn(COURSE_1);
		when(modelMapper.map(COURSE_DTO_1, Course.class)).thenReturn(COURSE_1);
		when(modelMapper.map(COURSE_1, CourseDto.class)).thenReturn(COURSE_DTO_1);
		CourseDto result = courseService.updateCourse(COURSE_DTO_1);
		assertNotNull(result);
		assertEquals(COURSE_DTO_1.getCourseId(), result.getCourseId());
	}

	@Test
	public void testUpdateCourse_InvalidCourse() throws ResourceNotFoundException {
		when(modelMapper.map(COURSE_DTO_1, Course.class)).thenReturn(COURSE_1);
		when(courseRepository.findById("IT1001")).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			courseService.updateCourse(COURSE_DTO_1);
		});
		assertEquals("No course present with the course ID : IT1001", exception.getMessage());
	}

	@Test
	public void testUpdateCourse_InvalidCategory() throws ResourceNotFoundException {
		when(modelMapper.map(COURSE_DTO_1, Course.class)).thenReturn(COURSE_1);
		when(courseRepository.findById("IT1001")).thenReturn(Optional.of(COURSE_1));
		when(categoryRepository.findById("IT")).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			courseService.updateCourse(COURSE_DTO_1);
		});
		assertEquals("Category ID IT doesn't exists", exception.getMessage());
	}

	@Test
	public void testDeleteCourse_ValidCourse() throws ResourceNotFoundException {
		when(courseRepository.findById("IT1001")).thenReturn(Optional.of(COURSE_1));
		doNothing().when(courseRepository).deleteById("IT1001");
		courseService.deleteCourse("IT1001");
		verify(courseRepository, times(1)).deleteById("IT1001");
	}

	@Test
	public void testDeleteCourse_InvalidCourse() throws ResourceNotFoundException {
		when(courseRepository.findById("IT1001")).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			courseService.deleteCourse("IT1001");
		});
		assertEquals("No course present with the course ID : IT1001", exception.getMessage());
	}

}
