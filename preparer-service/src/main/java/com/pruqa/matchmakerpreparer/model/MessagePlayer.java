package com.pruqa.matchmakerpreparer.model;

import lombok.Data;
import org.springframework.amqp.core.Message;

import java.util.Map;

@Data
public class MessagePlayer extends Message {

    private String gameName;

    private String playerId;

    private Map<String, Double> characteristics;
}
