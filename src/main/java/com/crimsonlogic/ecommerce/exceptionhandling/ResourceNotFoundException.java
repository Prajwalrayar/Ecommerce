package com.crimsonlogic.ecommerce.exceptionhandling;

/**
 * Thrown when the requested resource
 * cannot be found.
 */
public class ResourceNotFoundException extends Exception {

    // Parameterized Constructor.

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
