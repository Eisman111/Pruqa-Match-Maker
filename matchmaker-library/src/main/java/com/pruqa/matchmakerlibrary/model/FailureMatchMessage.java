package com.pruqa.matchmakerlibrary.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FailureMatchMessage extends ResultMessage {

    private ResponseCode failureCode;
    private String failureMessage;
    private String playerId;
}
