package com.pruqa.matchmakercaller.messanger;

import com.pruqa.matchmakerlibrary.model.SuccessMatchMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DefaultSuccessMatchConsumer implements SuccessMatchConsumer {

    @RabbitListener(queues = {"${app.success.rabbitmq.queue}"}, containerFactory = "listenerContainerFactory")
    @Override
    public void readMessage(SuccessMatchMessage messagePlayer) {
        log.info("OK {}", messagePlayer);
    }
}
