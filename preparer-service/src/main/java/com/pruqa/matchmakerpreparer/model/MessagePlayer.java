package com.pruqa.matchmakerpreparer.model;

import lombok.Data;

import java.util.Map;

@Data
public class MessagePlayer {

    private String gameName;

    private String playerId;

    private Map<String, Double> characteristics;
}
