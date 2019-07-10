package com.pruqa.matchmakerlibrary.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuccessMatchMessage {

    private String playerOneId;

    private String playerTwoId;

    private String gameName;
}
