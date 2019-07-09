package com.pruqa.matchmakersettings.utils;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ResponseCode {

    OK_RESPONSE(HttpStatus.OK,0,"Everything went according to plan"),
    INVALID_COMPANY(HttpStatus.BAD_REQUEST,210,"The company Id and game Id provided are not valid"),
    INVALID_GAME_SETTINGS(HttpStatus.BAD_REQUEST,102,"The game configurations are not defined correctly for the requested game"),
    INVALID_GAME_RULES(HttpStatus.BAD_REQUEST, 103,"The game rules are not defined for the requested game"),
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
