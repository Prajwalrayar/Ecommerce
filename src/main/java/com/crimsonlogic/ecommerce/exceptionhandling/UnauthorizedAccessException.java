package com.crimsonlogic.ecommerce.exceptionhandling;

/**
 * Thrown when a user attempts to
 * access an unauthorized operation.
 */
public class UnauthorizedAccessException extends Exception {

    public UnauthorizedAccessException(String message) {
        super(message);
    }

}