package com.billybolton.errors.constants;

/**
 * This class contains all the exception messages used in the application.
 */
public class ExceptionMessages {

    /**
     * Exception message for when a private constructor is called. Usually this message is needed in the constructor of
     * utility classes that contain only static methods.
     */
    public static final String PRIVATE_CONSTRUCTOR_MESSAGE = "This is a utility class and cannot be instantiated";

    private ExceptionMessages() {
        throw new UnsupportedOperationException(PRIVATE_CONSTRUCTOR_MESSAGE);
    }

}

