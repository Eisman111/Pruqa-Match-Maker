package com.pruqa.matchmakerpreparer.messanger;

import com.pruqa.matchmakerpreparer.model.MessagePlayer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DefaultPlayerProducer implements IPlayerProducer {

    private RabbitTemplate failureRabbitTemplate;

    @Autowired
    public DefaultPlayerProducer (final RabbitTemplate failureRabbitTemplate) {
        this.failureRabbitTemplate = failureRabbitTemplate;
    }

    @Override
    public void addToErrorQueue(MessagePlayer messagePlayer) {
        log.error("An error occurred here, {}", messagePlayer.toString());
        failureRabbitTemplate.convertAndSend(messagePlayer);
    }
}
