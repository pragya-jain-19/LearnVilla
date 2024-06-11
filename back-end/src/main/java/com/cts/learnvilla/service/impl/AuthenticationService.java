package com.cts.learnvilla.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.learnvilla.dto.JwtRequest;
import com.cts.learnvilla.dto.JwtResponse;
import com.cts.learnvilla.dto.StudentDto;
import com.cts.learnvilla.exception.ResourceAlreadyExistsException;
import com.cts.learnvilla.exception.ResourceNotFoundException;
import com.cts.learnvilla.model.Student;
import com.cts.learnvilla.repository.StudentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

	
	@Autowired
	private final StudentRepository usersRepository;
	@Autowired
	private final ModelMapper modelMapper;
	@Autowired
	private final PasswordEncoder passwordEncoder;
	@Autowired
	private final JwtService jwtService;
	@Autowired
	private final AuthenticationManager authenticationManager;

	public JwtResponse register(StudentDto userDto) {

		log.info("Registering user {}", userDto.getEmail());
		// Users user = toEntity(userDto);

		Student check = usersRepository.findByEmail(userDto.getEmail());
		if (check != null) {
			throw new ResourceAlreadyExistsException("Student already exits");
		}
		Student user = dtoToStudent(userDto);
		user.setRole("STUDENT");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Student savedUser = usersRepository.save(user);
		StudentDto savUserDto = studentToDto(savedUser);
		String token = jwtService.generateToken(savedUser);
		JwtResponse jwtResponse = new JwtResponse();
		jwtResponse.setToken(token);
		jwtResponse.setUser(savUserDto);

		log.info("User registered successfully");

		return jwtResponse;
	}

	public JwtResponse login(JwtRequest request) {
		log.info("Authenticating user {}", request.getEmail());
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		Student user = usersRepository.findByEmail(request.getEmail());
		if(user == null) {
			throw new ResourceNotFoundException("User does not exists");
		}
//				.orElseThrow(() -> new ResourceNotFoundException("User does not exists"));
		String token = jwtService.generateToken(user);

		StudentDto userDto = studentToDto(user);
		JwtResponse jwtResponse = new JwtResponse();
		jwtResponse.setToken(token);
		jwtResponse.setUser(userDto);
		log.info("User authenticated successfully");
		return jwtResponse;
	}

	public StudentDto studentToDto(Student s) {
		return this.modelMapper.map(s, StudentDto.class);
	}

	public Student dtoToStudent(StudentDto dto) {
		return this.modelMapper.map(dto, Student.class);
	}

}