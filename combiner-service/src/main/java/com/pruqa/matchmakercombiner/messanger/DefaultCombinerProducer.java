package com.pruqa.matchmakercombiner.messanger;

import com.pruqa.matchmakerlibrary.model.SuccessMatchMessage;
import com.pruqa.matchmakerlibrary.model.FailureMatchMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class DefaultCombinerProducer implements CombinerProducer {

    private RabbitTemplate successRabbitTemplate;
    private RabbitTemplate failureRabbitTemplate;

    public DefaultCombinerProducer(RabbitTemplate successRabbitTemplate, RabbitTemplate failureRabbitTemplate) {
        this.successRabbitTemplate = successRabbitTemplate;
        this.failureRabbitTemplate = failureRabbitTemplate;
    }

    @Override
    public void addToErrorQueue(final FailureMatchMessage failureMatchMessage) {
        failureRabbitTemplate.convertAndSend(failureMatchMessage);
    }

    @Override
    public void addToSuccessQueue(final SuccessMatchMessage successMatchMessage) {
        successRabbitTemplate.convertAndSend(successMatchMessage);
    }
}
