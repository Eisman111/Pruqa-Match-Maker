package com.pruqa.matchmakerstartercontroller.controller;

import com.pruqa.matchmakerstartercontroller.exception.StarterApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.pruqa.matchmakerstartercontroller.utils.ResponseCode.UNKNOWN_EXCEPTION;

@Slf4j
@ControllerAdvice
public class StarterControllerAdvice extends ResponseEntityExceptionHandler {

    /**
     * Common API exception handler
     * @return apiError
     */
    @ExceptionHandler(value = {StarterApiException.class})
    public ResponseEntity<String> handleApiExceptions(StarterApiException spe) {
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
