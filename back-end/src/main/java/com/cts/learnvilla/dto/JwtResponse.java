package com.cts.learnvilla.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {

	private String token;
	private StudentDto user;
//	private UserDto user;
}