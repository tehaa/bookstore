package com.assessment.bookstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public static final String MESSAGE_FORMAT = "Bad request at %s with %s = %s";

	public BadRequestException() {
		super();
	}

	public BadRequestException(String message) {
		super(message);
	}

	public BadRequestException(String entity, String field, String value) {
		super(String.format(MESSAGE_FORMAT, entity, field, value));
	}
}
