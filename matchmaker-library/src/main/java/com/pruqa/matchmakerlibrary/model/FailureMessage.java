package com.pruqa.matchmakerlibrary.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FailureMessage {

    private ResponseCode failureCode;
    private String failureMessage;
    private String playerId;
    private String gameName;
}
