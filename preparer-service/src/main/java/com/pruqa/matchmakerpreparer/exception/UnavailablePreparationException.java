package com.pruqa.matchmakerpreparer.exception;

public class UnavailablePreparationException extends RuntimeException {

    public UnavailablePreparationException(String message) {
        super(message);
    }

    public UnavailablePreparationException(String message, Throwable cause) {
        super(message, cause);
    }
}
