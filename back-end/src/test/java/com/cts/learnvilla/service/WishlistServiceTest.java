package com.cts.learnvilla.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.cts.learnvilla.dto.WishlistDto;
import com.cts.learnvilla.exception.ResourceAlreadyExistsException;
import com.cts.learnvilla.exception.ResourceNotFoundException;
import com.cts.learnvilla.model.Category;
import com.cts.learnvilla.model.Course;
import com.cts.learnvilla.model.Student;
import com.cts.learnvilla.model.Wishlist;
import com.cts.learnvilla.repository.CourseRepository;
import com.cts.learnvilla.repository.StudentRepository;
import com.cts.learnvilla.repository.WishlistRepository;
import com.cts.learnvilla.service.impl.WishlistServiceImpl;

@ExtendWith(MockitoExtension.class)
public class WishlistServiceTest {

	private final Student STUDENT = new Student(1, "Pragya", "Jain", "8743958309", "pragya@gmail.com", "Pragya@12", "Pragya@12", null, null, true, "STUDENT");
	private final Course COURSE = new Course("IT1001", "Java Programming", "Java", new Category("IT", "IT", null), 48.0, 4500.0, 0, 0.0, "image", "English", null, null, null, null); 
	private final Course COURSE_2 = new Course("IT1002", "Java Programming", "Java", new Category("IT", "IT", null), 48.0, 4500.0, 0, 0.0, "image", "English", null, null, null, null); 
	private final WishlistDto WISHLIST_DTO = new WishlistDto(1, STUDENT.getStudentId(), Arrays.asList(COURSE.getCourseId()), 0.0);
	private final Wishlist WISHLIST = new Wishlist(1, STUDENT, Arrays.asList(COURSE), 0.0);
//	private final WishlistDto WISHLIST_DTO_2 = new WishlistDto(1, STUDENT.getStudentId(), Arrays.asList(COURSE.getCourseId(), COURSE_2.getCourseId()), 0.0);
//	private final Wishlist WISHLIST_2 = new Wishlist(1, STUDENT, Arrays.asList(COURSE, COURSE_2), 0.0);
	
//	private final Category CATEGORY = new Category("IT", "IT", null);
//	private final CourseDto COURSE_DTO_1 = new CourseDto("IT1001", "Java Programming", "Java", "IT", 48.0, 4500.0, 0, 0.0, "image", "English"); 
//	private final Course COURSE_1 = new Course("IT1001", "Java Programming", "Java", CATEGORY, 48.0, 4500.0, 0, 0.0, "image", "English", null, null, null, null), COURSE_2 = new Course("IT1002", "Python Programming", "Python", CATEGORY, 48.0, 4500.0, 0, 0.0, "image", "English", null, null, null, null); 
//	private final List<Course> COURSES = Arrays.asList(COURSE_1, COURSE_2);
	
	@Mock
	private ModelMapper modelMapper;
	
	@Mock
	private WishlistRepository wishlistRepository;
	
	@Mock
	private StudentRepository studentRepository;
	
	@Mock
	private CourseRepository courseRepository;
	
	@InjectMocks
	private WishlistServiceImpl wishlistService;
	
	@Test
	public void testInsertCourseIntoWishlist_NewStudentWithNoCart() throws ResourceNotFoundException, ResourceAlreadyExistsException {
		when(modelMapper.map(WISHLIST, WishlistDto.class)).thenReturn(WISHLIST_DTO);
		when(courseRepository.findById("IT1001")).thenReturn(Optional.of(COURSE));
		when(studentRepository.findById(1)).thenReturn(Optional.of(STUDENT));
		when(wishlistRepository.findByStudent_StudentId(1)).thenReturn(null);
		when(wishlistRepository.save(any(Wishlist.class))).thenReturn(WISHLIST);
		WishlistDto result = wishlistService.insertCourseIntoWishlist("IT1001", 1);
		assertEquals(WISHLIST_DTO.getWishlistId(), result.getWishlistId());
	}
	
//	@Test
//	public void testInsertCourseIntoWishlist_OldStudentHavingCart() throws ResourceNotFoundException, ResourceAlreadyExistsException {
//		when(modelMapper.map(WISHLIST_2, WishlistDto.class)).thenReturn(WISHLIST_DTO_2);
//		when(courseRepository.findById("IT1002")).thenReturn(Optional.of(COURSE_2));
//		when(studentRepository.findById(1)).thenReturn(Optional.of(STUDENT));
//		when(wishlistRepository.findByStudent_StudentId(1)).thenReturn(WISHLIST);
//		when(wishlistRepository.save(any(Wishlist.class))).thenReturn(WISHLIST_2);
//		WishlistDto result = wishlistService.insertCourseIntoWishlist("IT1002", 1);
//		assertEquals(WISHLIST_DTO_2.getWishlistId(), result.getWishlistId());
//	}
	
	@Test
	public void testInsertCourseIntoWishlist_OldStudentHavingCartWithCourseAlreadyAdded() throws ResourceNotFoundException, ResourceAlreadyExistsException {
//		when(modelMapper.map(WISHLIST_2, WishlistDto.class)).thenReturn(WISHLIST_DTO_2);
		when(courseRepository.findById("IT1001")).thenReturn(Optional.of(COURSE));
		when(studentRepository.findById(1)).thenReturn(Optional.of(STUDENT));
		when(wishlistRepository.findByStudent_StudentId(1)).thenReturn(WISHLIST);
		Exception exception = assertThrows(ResourceAlreadyExistsException.class, ()->{
			wishlistService.insertCourseIntoWishlist("IT1001", 1);
		});
		assertEquals("Course already exists in wishlist", exception.getMessage());
	}
	
	@Test
	public void testInsertCourseIntoWishlist_InvalidCourse() throws ResourceNotFoundException, ResourceAlreadyExistsException {
		when(courseRepository.findById("IT1001")).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
			wishlistService.insertCourseIntoWishlist("IT1001", 1);
		});
		assertEquals("No course available with the course ID IT1001", exception.getMessage());
	}
	
	@Test
	public void testInsertCourseIntoWishlist_InvalidStudent() throws ResourceNotFoundException, ResourceAlreadyExistsException {
		when(courseRepository.findById("IT1001")).thenReturn(Optional.of(COURSE));
		when(studentRepository.findById(1)).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
			wishlistService.insertCourseIntoWishlist("IT1001", 1);
		});
		assertEquals("Student ID 1 doesn't exists", exception.getMessage());
	}
	
//	@Test
//	public void testDeleteCourseFromWishlist() throws ResourceNotFoundException {
//		
//	}
	
	@Test
	public void testDeleteCourseFromWishlist_InvalidStudent() throws ResourceNotFoundException {
		when(studentRepository.findById(1)).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
			wishlistService.deleteCourseFromWishlist("IT1001", 1);
		});
		assertEquals("Student ID 1 doesn't exists", exception.getMessage());
	}
	
	@Test
	public void testDeleteCourseFromWishlist_InvalidCourse() throws ResourceNotFoundException {
		when(studentRepository.findById(1)).thenReturn(Optional.of(STUDENT));
		when(courseRepository.findById("IT1001")).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
			wishlistService.deleteCourseFromWishlist("IT1001", 1);
		});
		assertEquals("No course available with the course ID IT1001", exception.getMessage());
	}
	
	@Test
	public void testDeleteCourseFromWishlist_EmptyWishlist() throws ResourceNotFoundException {
		when(studentRepository.findById(1)).thenReturn(Optional.of(STUDENT));
		when(courseRepository.findById("IT1001")).thenReturn(Optional.of(COURSE));
		when(wishlistRepository.findByStudent_StudentId(1)).thenReturn(null);
		Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
			wishlistService.deleteCourseFromWishlist("IT1001", 1);
		});
		assertEquals("No wishlist present corresponding to student ID : 1", exception.getMessage());
	}
	
	@Test
	public void testDeleteCourseFromWishlist_CourseNotExistsInWishlist() throws ResourceNotFoundException {
		when(studentRepository.findById(1)).thenReturn(Optional.of(STUDENT));
		when(courseRepository.findById("IT1002")).thenReturn(Optional.of(COURSE_2));
		when(wishlistRepository.findByStudent_StudentId(1)).thenReturn(WISHLIST);
		Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
			wishlistService.deleteCourseFromWishlist("IT1002", 1);
		});
		assertEquals("Course not present in the wishlist", exception.getMessage());
	}
	
	@Test
	public void testViewWishlistByStudentId() throws ResourceNotFoundException {
		when(studentRepository.findById(1)).thenReturn(Optional.of(STUDENT));
		when(wishlistRepository.findByStudent_StudentId(1)).thenReturn(WISHLIST);
		Wishlist result = wishlistService.viewWishlistByStudentId(1);
		assertEquals(WISHLIST.getWishlistId(), result.getWishlistId());
	}
	
	@Test
	public void testViewWishlistByStudentId_InvalidStudent() throws ResourceNotFoundException {
		when(studentRepository.findById(1)).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
			wishlistService.viewWishlistByStudentId(1);
		});
		assertEquals("Student ID 1 doesn't exists", exception.getMessage());
	}
	
	@Test
	public void testViewWishlistByStudentId_NoWishlist() throws ResourceNotFoundException {
		when(studentRepository.findById(1)).thenReturn(Optional.of(STUDENT));
		when(wishlistRepository.findByStudent_StudentId(1)).thenReturn(null);
		Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
			wishlistService.viewWishlistByStudentId(1);
		});
		assertEquals("No wishlist corresponding to student ID 1", exception.getMessage());
	}
	
	@Test
	public void testDeleteWishlistByStudentId() throws ResourceNotFoundException {
		when(studentRepository.findById(1)).thenReturn(Optional.of(STUDENT));
		when(wishlistRepository.findByStudent_StudentId(1)).thenReturn(WISHLIST);
		doNothing().when(wishlistRepository).deleteById(1);
		Integer result = wishlistService.deleteWishlistByStudentId(1);
		assertEquals(1, result);
	}
	
	@Test
	public void testDeleteWishlistByStudentId_InvalidStudent() throws ResourceNotFoundException {
		when(studentRepository.findById(1)).thenReturn(Optional.empty());
		Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
			wishlistService.deleteWishlistByStudentId(1);
		});
		verify(studentRepository, times(1)).findById(1);
		assertEquals("Student ID 1 doesn't exists", exception.getMessage());
	}
	
	@Test
	public void testDeleteWishlistByStudentId_NoWishlist() throws ResourceNotFoundException {
		when(studentRepository.findById(1)).thenReturn(Optional.of(STUDENT));
		when(wishlistRepository.findByStudent_StudentId(1)).thenReturn(null);
		Exception exception = assertThrows(ResourceNotFoundException.class, ()->{
			wishlistService.deleteWishlistByStudentId(1);
		});
		assertEquals("No wishlist corresponding to student ID 1", exception.getMessage());
	}
	
}
