package com.pruqa.matchmakercombiner.model;

import lombok.Data;

@Data
public class GameAttribute {

    // ==== fields ====
    private Long id;

    private String attributeName;

    private Double attributeMultiplier;

    private Double absoluteRange;

    private Double percentageRange;

    private int incrementalWaitTime;

    private int incrementalRounds;
}
