package com.pruqa.matchmakercombiner.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MatchedPlayerMessage {

    private String playerOneId;

    private String playerTwoId;

    private String gameName;
}
