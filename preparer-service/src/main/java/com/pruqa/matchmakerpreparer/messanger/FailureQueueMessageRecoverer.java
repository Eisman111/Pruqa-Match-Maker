package com.pruqa.matchmakerpreparer.messanger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pruqa.matchmakerlibrary.model.FailureMessage;
import com.pruqa.matchmakerlibrary.model.ResponseCode;
import com.pruqa.matchmakerpreparer.model.MessagePlayer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;

import java.io.IOException;

@Slf4j
public class FailureQueueMessageRecoverer extends RejectAndDontRequeueRecoverer {

    private PlayerProducer messageProducer;

    public FailureQueueMessageRecoverer (PlayerProducer producer) {
        this.messageProducer = producer;
    }

    @Override
    public void recover(Message message, Throwable cause) {

        try {
            MessagePlayer messagePlayer = new ObjectMapper().readValue(message.getBody(),MessagePlayer.class);

            messageProducer.addToErrorQueue(FailureMessage
                    .builder()
                    .failureCode(ResponseCode.GENERIC_ERROR)
                    .failureMessage(cause.getMessage())
                    .playerId(messagePlayer.getPlayerId())
                    .gameName(messagePlayer.getGameName())
                    .build());

        } catch (IOException e) {
            log.error("Failed to add failed message {} to the queue for {}", message, e.getMessage());
        }

        super.recover(message, cause);
    }
}
