package org.loop.troop.exception;

import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException {
    
    private final HttpStatus status;
    private final String error;

    // Constructor for custom message, status, and error type
    public ServiceException(String message, HttpStatus status, String error) {
        super(message);
        this.status = status;
        this.error = error;
    }

    // Constructor for just the message and status (for generic error handling)
    public ServiceException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.error = status.getReasonPhrase();
    }

    // Getter for status
    public HttpStatus getStatus() {
        return status;
    }

    // Getter for error type
    public String getError() {
        return error;
    }
}
