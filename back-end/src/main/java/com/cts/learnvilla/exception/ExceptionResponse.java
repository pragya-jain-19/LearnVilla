package com.cts.learnvilla.exception;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionResponse {

	@JsonFormat(pattern = "yyyy-MM-dd HH:MM:SS")
	private LocalDateTime timestamp;
	private String message;
	private String statusCode;
	private String url;

}
