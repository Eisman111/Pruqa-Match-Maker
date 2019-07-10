package com.pruqa.matchmakerlibrary.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuccessMatchMessage extends ResultMessage {

    private String playerOneId;

    private String playerTwoId;
}
