package com.cts.learnvilla.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginCredentialsDto {

	private Integer studentId;

	@NotNull
	@Email(message="Email ID should be valid")
//	@Pattern(regexp = "[A-Za-z0-9.]+@[A-Za-z]{3,15}.[A-Za-z]{2,3}", message="Email ID should be valid")
	private String email;

	@NotNull
	@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "Password should contain atleast 1 uppercase character, 1 lowercase character, 1 numerical digit and 1 special character")
	private String password;

	@NotNull
	@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "Password should contain atleast 1 uppercase character, 1 lowercase character, 1 numerical digit and 1 special character")
	private String confirmPassword;

	private String role = "STUDENT";

}
