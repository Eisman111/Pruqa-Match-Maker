package com.pruqa.matchmakerlibrary.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ResponseCode {
    NO_MATCHING_PLAYER(400, "No matching player found"),
    GENERIC_ERROR(500, "Unknown error");

    private int responseCode;
    private String genericMessage;

    ResponseCode(int responseCode, String genericMessage) {
        this.responseCode = responseCode;
        this.genericMessage = genericMessage;
    }
}
