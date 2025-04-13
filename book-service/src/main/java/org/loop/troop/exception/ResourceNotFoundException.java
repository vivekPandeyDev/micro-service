package org.loop.troop.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, String identifierType, Object identifierValue) {
        super(String.format("%s not found with %s: %s", resourceName, identifierType, identifierValue));
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
