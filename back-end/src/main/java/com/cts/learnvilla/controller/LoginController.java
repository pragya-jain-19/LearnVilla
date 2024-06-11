package com.cts.learnvilla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.learnvilla.dto.JwtRequest;
import com.cts.learnvilla.dto.JwtResponse;
import com.cts.learnvilla.dto.StudentDto;
import com.cts.learnvilla.service.impl.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/learnvilla/api")
public class LoginController {
	
	@Autowired
	private AuthenticationService authenticationService;

	/**
	 * Login student
	 * 
	 * @param loginCredentialsDto
	 * @return
	 */
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> loginStudent(@RequestBody JwtRequest jwtRequest) {
		return new ResponseEntity<> (authenticationService.login(jwtRequest), HttpStatus.OK);
	}

	/**
	 * Register student
	 * 
	 * @param studentDto
	 * @return
	 */
	@PostMapping("/register")
	public ResponseEntity<JwtResponse> registerStudent(@Valid @RequestBody StudentDto studentDto) {
	
		return new ResponseEntity<>(authenticationService.register(studentDto), HttpStatus.CREATED);
	}

}
