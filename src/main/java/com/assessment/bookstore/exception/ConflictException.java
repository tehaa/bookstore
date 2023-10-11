package com.assessment.bookstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public static final String MESSAGE_FORMAT = "Conflict in %s with %s = %s";

	public ConflictException() {

	}

	public ConflictException(String message) {
		super(message);
	}

	public ConflictException(String entity, String field, String value) {
		super(String.format(MESSAGE_FORMAT, entity, field, value));
	}
}
