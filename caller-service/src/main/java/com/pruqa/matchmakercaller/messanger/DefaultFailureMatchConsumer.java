package com.pruqa.matchmakercaller.messanger;

import com.pruqa.matchmakerlibrary.model.SuccessMatchMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DefaultFailureMatchConsumer implements FailureMatchConsumer {

    @RabbitListener(queues = {"${app.failure.rabbitmq.queue}"}, containerFactory = "listenerContainerFactory")
    @Override
    public void readMessage(SuccessMatchMessage messagePlayer) {
        log.error("ERROR {}", messagePlayer);
    }
}
