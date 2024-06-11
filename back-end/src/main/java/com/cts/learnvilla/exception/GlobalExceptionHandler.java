package com.cts.learnvilla.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Method to handle ResourceNotFoundException
	 * 
	 * @param req
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ExceptionResponse> resourceNotFoundExceptionHandler(HttpServletRequest req,
			ResourceNotFoundException ex) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(),
				HttpStatus.NOT_FOUND.toString(), req.getRequestURI());
		log.error(ex.getMessage());
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND);
	}

	/**
	 * Method to handle MethodArgumentNotValidException
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> methodsArgumentsNotValidExceptionHandler(
			MethodArgumentNotValidException ex) {
		Map<String, String> errors = new LinkedHashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});
		log.error("Errors : {}", errors);
		return new ResponseEntity<Map<String, String>>(errors, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Method to handle PaymentNotDoneException
	 * 
	 * @param req
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(PaymentNotDoneException.class)
	public ResponseEntity<ExceptionResponse> paymentNotDoneExceptionHandler(HttpServletRequest req,
			PaymentNotDoneException ex) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(),
				HttpStatus.BAD_REQUEST.toString(), req.getRequestURI());
		log.error(ex.getMessage());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Method to handle InvalidCredentials
	 * 
	 * @param req
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(InvalidCredentials.class)
	public ResponseEntity<ExceptionResponse> invalidCredentialsExceptionHandler(HttpServletRequest req,
			InvalidCredentials ex) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(),
				HttpStatus.BAD_REQUEST.toString(), req.getRequestURI());
		log.error(ex.getMessage());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Method to handle ResourceAlreadyExistsException
	 * 
	 * @param req
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public ResponseEntity<ExceptionResponse> resourceAlreadyExistsExceptionHandler(HttpServletRequest req,
			ResourceAlreadyExistsException ex) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(),
				HttpStatus.BAD_REQUEST.toString(), req.getRequestURI());
		log.error(ex.getMessage());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Method to handle all exceptions
	 * 
	 * @param req
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> allExceptionHandler(HttpServletRequest req, Exception ex) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(),
				HttpStatus.BAD_REQUEST.toString(), req.getRequestURI());
		log.error(ex.getMessage());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

}
