package com.pruqa.matchmakercombiner.model;

import lombok.Data;

import java.util.Set;

@Data
public class GameSetting {

    private Long id;

    private GameMode matchMode;

    private Set<GameAttribute> attributes;

    private Double absoluteRange;

    private Double percentageRange;

    private int incrementalWaitTime;

    private int incrementalRounds;
}
