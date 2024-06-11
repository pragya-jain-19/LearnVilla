package com.cts.learnvilla.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PaymentNotDoneException extends RuntimeException {
	
	public PaymentNotDoneException(String message) {
		super(message);
	}
	
}
