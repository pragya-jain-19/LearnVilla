package com.cts.learnvilla.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentDto {
	
	private Integer enrollmentId;
	
	@NotNull
	private Integer studentId;
	
	@NotNull
	@Pattern(regexp = "^[A-Z]{2}[0-9]{4}$", message = "Course ID should be of only 6 characters : First 2 characters in uppercase followed by 4 digits")
	private String courseId;
	
	@DecimalMin("0.0")
	private Double progress = 0.0;
	
	private Boolean isProgressLeft = true;
	
	private LocalDateTime enrolledDate = LocalDateTime.now();
	
}
