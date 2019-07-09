package com.pruqa.matchmakersettings.exceptions;

import com.pruqa.matchmakersettings.utils.ResponseCode;

public class SettingsApiException extends RuntimeException {

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    SettingsApiException() {
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
    public SettingsApiException(String message) {
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
