package com.cts.learnvilla.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {

	@NotNull
	@Pattern(regexp = "^[A-Z]{2}[0-9]{4}$", message = "Course ID should be of only 6 characters : First 2 characters in uppercase followed by 4 digits")
	private String courseId;
	
	@NotBlank(message = "Course Name cannot be blank")
	@Size(min = 2, message = "Course should contain minimum 2 characters")
	private String courseName;
	
	@NotBlank(message = "Description cannot be blank")
	@Size(min = 2, message = "Description should contain minimum 2 characters")
	private String description;
	
	@NotNull(message = "Category ID cannot be null")
	@Pattern(regexp = "^[A-Z]{2}$", message = "Category ID should contsain 2 characters in uppercase only")
	private String categoryId;
	
	private Double duration;
	
	@NotNull(message = "Price cannot be null")
	@DecimalMin(value="100.0", message = "Price should be greater than 100.0")
	private Double price;
	
	private Integer studentsEnrolled = 0;
	
	@DecimalMin(value="0.0", message = "Rating should be greater than 0.0")
	@DecimalMax(value="5.0", message = "Rating should be less than 5.0")
	private Double rating = 0.0;
	
	private String courseImage;
	
	@Size(min = 5, message = "Language should contain atleast 5 characters")
	private String language = "English";
	
//	private String createdDate;
//	
//	private String updatedDate;

}
