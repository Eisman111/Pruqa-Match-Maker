package com.pruqa.matchmakerpreparer.messanger;

import com.pruqa.matchmakerpreparer.model.MessagePlayer;
import com.pruqa.matchmakerpreparer.model.Player;
import com.pruqa.matchmakerpreparer.service.CounterPreparerService;
import com.pruqa.matchmakerpreparer.service.IPreparerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DefaultPlayerConsumer implements PlayerConsumer {

    private IPreparerService service;

    @Autowired
    public DefaultPlayerConsumer (final CounterPreparerService service) {
        this.service = service;
    }

    @Override
    @RabbitListener(queues = {"${app.rabbitmq.queue}"},
    containerFactory = "listenerContainerFactory")
    public void readMessage(final MessagePlayer message) {
        log.info(">>>>>>>>>>>>>> {}", message.toString());
        Player player = service.prepareFlow(message);
        log.info(">>> {}", player.toString());
    }
}
