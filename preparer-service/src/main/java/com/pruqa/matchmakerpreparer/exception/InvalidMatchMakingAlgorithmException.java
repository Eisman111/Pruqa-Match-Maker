package com.pruqa.matchmakerpreparer.exception;

public class InvalidMatchMakingAlgorithmException extends RuntimeException {

    public InvalidMatchMakingAlgorithmException(String message) {
        super(message);
    }

    public InvalidMatchMakingAlgorithmException(String message, Throwable cause) {
        super(message, cause);
    }
}
