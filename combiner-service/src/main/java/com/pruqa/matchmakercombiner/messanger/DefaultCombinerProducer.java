package com.pruqa.matchmakercombiner.messanger;

import com.pruqa.matchmakercombiner.model.MatchedPlayerMessage;
import com.pruqa.matchmakerlibrary.model.FailureMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class DefaultCombinerProducer implements CombinerProducer {

    private RabbitTemplate successRabbitTemplate;
    private RabbitTemplate failureRabbitTemplate;

    public DefaultCombinerProducer(RabbitTemplate successRabbitTemplate, RabbitTemplate failureRabbitTemplate) {
        this.successRabbitTemplate = successRabbitTemplate;
        this.failureRabbitTemplate = failureRabbitTemplate;
    }

    @Override
    public void addToErrorQueue(final FailureMessage failureMessage) {
        failureRabbitTemplate.convertAndSend(failureMessage);
    }

    @Override
    public void addToSuccessQueue(final MatchedPlayerMessage matchedPlayerMessage) {
        successRabbitTemplate.convertAndSend(matchedPlayerMessage);
    }
}
