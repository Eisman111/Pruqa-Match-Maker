package com.pruqa.matchmakerlibrary.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ResponseCode {
    MATCHED,
    NO_MATCHING_PLAYER,
    GENERIC_ERROR
}
