package com.cts.learnvilla.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRequest {

//	private String username;
	private String email;
	private String password;
}