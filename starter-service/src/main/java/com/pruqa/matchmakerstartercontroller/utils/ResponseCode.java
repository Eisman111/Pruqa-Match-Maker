package com.pruqa.matchmakerstartercontroller.utils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ResponseCode {
    OK_RESPONSE(HttpStatus.OK,0,"Everything went according to plan"),
    INVALID_GAME(HttpStatus.BAD_REQUEST,102,"The game and the company can't be found"),
    INVALID_HEADERS(HttpStatus.BAD_REQUEST, 300,"The request headers are not valid"),
    UNKNOWN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, 500,"Unexpected problem, contact the support team");

    // ==== fields ====
    private HttpStatus status;
    private int responseCode;
    private String message;

    // ==== constructor ====
    /**
     * Enum constructor for ResponseCode
     * @param status the http response status based on the result
     * @param message the default description message
     */
    ResponseCode(HttpStatus status, int responseCode, String message) {
        this.status = status;
        this.responseCode = responseCode;
        this.message = message;
    }
}
