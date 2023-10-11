package com.assessment.bookstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public static final String MESSAGE_FORMAT = "%s with %s = %s not found.";

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String entity, String field, String value) {

        super(String.format(MESSAGE_FORMAT, entity, field, value));

    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
