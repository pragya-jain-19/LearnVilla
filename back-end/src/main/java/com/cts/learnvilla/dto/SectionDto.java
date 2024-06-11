package com.cts.learnvilla.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectionDto {

	private Integer sectionId;

	@NotNull
	@Pattern(regexp = "^[A-Z]{2}[0-9]{4}$", message = "Course ID should be of only 6 characters : First 2 characters in uppercase followed by 4 digits")
	private String courseId;

	@NotNull
	private Integer sectionNumber;

	@NotNull
	@Size(min = 5, message = "Section name should contain atleast 5 characters")
	private String sectionName;

	private String sectionContent;

	private String videoUrl;

}
