package com.pruqa.matchmakersettings.controller;

import com.pruqa.matchmakersettings.exceptions.SettingsApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.pruqa.matchmakersettings.utils.ResponseCode.*;

@Slf4j
@ControllerAdvice
public class SettingsControllerAdvice extends ResponseEntityExceptionHandler {

    /**
     * Common API exception handler
     * @return apiError
     */
    @ExceptionHandler(value = {SettingsApiException.class})
    public ResponseEntity<String> handleApiExceptions(SettingsApiException spe) {
        return ResponseEntity
                .status(spe.getResponseCode().getStatus())
                .body(spe.getResponseCode().getMessage());
    }

    /**
     * Generic exception handler
     * @param re runtime exception
     * @return apiError
     */
    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<String> handleGenericRuntimeException(RuntimeException re) {
        log.error("A generic exception has been generated, the error message: {}, cause: {}", re.getMessage(), re.getCause());
        return ResponseEntity
                .status(UNKNOWN_EXCEPTION.getStatus())
                .body(UNKNOWN_EXCEPTION.getMessage());
    }
}
