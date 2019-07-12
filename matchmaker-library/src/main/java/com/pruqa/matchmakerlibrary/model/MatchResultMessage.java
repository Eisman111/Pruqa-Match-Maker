package com.pruqa.matchmakerlibrary.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchResultMessage {

    private ResponseCode responseCode;
    private String responseMessage;
    private String gameName;
    private String inputPlayer;
    private String matchedPlayer;
}
