package com.pruqa.matchmakercaller.messanger;

import com.pruqa.matchmakercaller.generated.settings.invoker.ApiException;
import com.pruqa.matchmakercaller.service.IResultMatchService;
import com.pruqa.matchmakerlibrary.model.MatchResultMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DefaultMatchResultConsumer implements MatchResultConsumer {

    private IResultMatchService service;

    @Autowired
    public DefaultMatchResultConsumer(IResultMatchService service) {
        this.service = service;
    }

    @RabbitListener(queues = {"${app.result.rabbitmq.queue}"}, containerFactory = "listenerContainerFactory")
    @Override
    public void readMessage(MatchResultMessage messagePlayer) {
        try {
            service.notifyResultFlow(messagePlayer);
        } catch (ApiException e) {
            log.error("Error while trying to contact the settings service for recovering the api details" +
                    " of the company {} for errorCode: {}, errorMessage: {}",
                    messagePlayer.toString(), e.getCode(), e.getResponseBody());
        }
    }
}
