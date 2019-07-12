package com.pruqa.matchmakercombiner.messanger;

import com.pruqa.matchmakerlibrary.model.MatchResultMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class DefaultCombinerProducer implements CombinerProducer {

    private RabbitTemplate rabbitTemplate;

    public DefaultCombinerProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void addToResultQueue(final MatchResultMessage matchResultMessage) {
        rabbitTemplate.convertAndSend(matchResultMessage);
    }
}
