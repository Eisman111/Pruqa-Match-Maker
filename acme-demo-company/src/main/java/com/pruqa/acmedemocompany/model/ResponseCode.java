package com.pruqa.acmedemocompany.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ResponseCode {

    MATCHED,
    INVALID_PLAYER_ATTRIBUTES,
    NO_MATCHING_PLAYERS,
    GENERIC_ERROR
}
