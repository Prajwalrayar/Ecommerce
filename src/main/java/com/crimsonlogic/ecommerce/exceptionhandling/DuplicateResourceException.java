package com.crimsonlogic.ecommerce.exceptionhandling;

/**
 * Thrown when attempting to create a resource
 * that already exists.
 */
public class DuplicateResourceException extends Exception {

    // Parameterized Constructor.
    public DuplicateResourceException(String message) {
        super(message);
    }

}