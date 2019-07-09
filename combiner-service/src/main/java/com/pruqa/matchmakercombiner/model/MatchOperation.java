package com.pruqa.matchmakercombiner.model;

import lombok.Data;

@Data
public class MatchOperation {

    private int tentative;

    public static MatchOperation defaultInstanceOfMatchStatus() {
        MatchOperation matchOperation = new MatchOperation();
        matchOperation.setTentative(1);
        return matchOperation;
    }
}
