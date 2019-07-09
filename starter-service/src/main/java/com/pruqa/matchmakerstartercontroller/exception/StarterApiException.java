package com.pruqa.matchmakerstartercontroller.exception;

import com.pruqa.matchmakerstartercontroller.utils.ResponseCode;

public class StarterApiException extends RuntimeException {

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    StarterApiException() {
        super();
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public StarterApiException(String message) {
        super(message);
    }

    /**
     * Recover the response code of the exception
     *
     * @return response code
     */
    public ResponseCode getResponseCode() {
        return ResponseCode.UNKNOWN_EXCEPTION;
    }
}
