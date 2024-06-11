package com.cts.learnvilla.dto;

import jakarta.validation.constraints.Email;
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
public class StudentDto {

	private Integer studentId;

	@NotBlank(message = "First name cannot be blank")
	@Size(min = 3, message = "First name should be of atleast 3 characters")
	@Pattern(regexp = "[A-Za-z ]+", message = "First name should only contain characters")
	private String firstName;

	@Pattern(regexp = "[A-Za-z ]+", message = "Last name should only contain characters")
	private String lastName;

	@Pattern(regexp = "[0-9+-]{10,15}", message = "Mobile number should be between 10 to 15 valid characters {0-9,+,-}")
	private String mobile;

	@NotNull
	@Email(message="Email ID should be valid")
//	@Pattern(regexp = "[A-Za-z0-9.]+@[A-Za-z]{3,15}.[A-Za-z]{2,3}", message="Email ID should be valid")
	private String email;

	@NotNull
	@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "Password should contain atleast 1 uppercase character, 1 lowercase character, 1 numerical digit and 1 special character")
	private String password;

//	@NotNull
	@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "Password should contain atleast 1 uppercase character, 1 lowercase character, 1 numerical digit and 1 special character")
	private String confirmPassword;

	private Boolean isActive = true;

//	@JsonIgnore
	private String role = "STUDENT";

}
