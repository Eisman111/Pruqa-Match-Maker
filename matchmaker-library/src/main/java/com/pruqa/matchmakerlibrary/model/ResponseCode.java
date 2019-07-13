package com.pruqa.matchmakerlibrary.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ResponseCode {
    MATCHED(200),
    INVALID_PLAYER_ATTRIBUTES(400),
    NO_MATCHING_PLAYER(404),
    GENERIC_ERROR(500);

    private int responseCode;

    ResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
}
