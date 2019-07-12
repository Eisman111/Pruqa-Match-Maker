package com.pruqa.matchmakerpreparer.messanger;

import com.pruqa.matchmakerlibrary.model.MatchResultMessage;
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
    public void addToErrorQueue(MatchResultMessage matchResultMessage) {
        log.error("An error occurred here, {}", matchResultMessage.toString());
        failureRabbitTemplate.convertAndSend(matchResultMessage);
    }
}
