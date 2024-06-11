package com.cts.learnvilla.dto;

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
public class CategoryDto {

	@NotNull
	@Pattern(regexp = "^[A-Z]{2}$", message = "Category ID should contain 2 characters in uppercase only")
	private String categoryId;

	@NotBlank(message = "Category cannot be blank")
	@Size(min = 2, message = "Category should contain minimum 2 characters")
	private String categoryName;

}
