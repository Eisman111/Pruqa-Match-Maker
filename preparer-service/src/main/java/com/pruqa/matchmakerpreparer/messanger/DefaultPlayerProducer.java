package com.pruqa.matchmakerpreparer.messanger;

import com.pruqa.matchmakerlibrary.model.FailureMatchMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DefaultPlayerProducer implements PlayerProducer {

    private RabbitTemplate failureRabbitTemplate;

    @Autowired
    public DefaultPlayerProducer (final RabbitTemplate failureRabbitTemplate) {
        this.failureRabbitTemplate = failureRabbitTemplate;
    }

    @Override
    public void addToErrorQueue(FailureMatchMessage failureMatchMessage) {
        log.error("An error occurred here, {}", failureMatchMessage.toString());
        failureRabbitTemplate.convertAndSend(failureMatchMessage);
    }
}
