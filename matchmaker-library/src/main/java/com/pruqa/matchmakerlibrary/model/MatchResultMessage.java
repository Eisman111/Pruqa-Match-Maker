package com.pruqa.matchmakerlibrary.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MatchResultMessage {

    private ResponseCode responseCode;
    private String responseMessage;
    private String gameName;
    private String inputPlayer;
    private String matchedPlayer;
}
