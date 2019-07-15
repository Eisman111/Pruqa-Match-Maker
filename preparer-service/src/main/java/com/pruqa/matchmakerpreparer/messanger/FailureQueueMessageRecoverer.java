package com.pruqa.matchmakerpreparer.messanger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pruqa.matchmakerlibrary.model.MatchResultMessage;
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

    /**
     * Once if failed to recover a message from the queue send it to the error queue and throw a AmqpRejectAndDontRequeueException
     * @param message queue message
     * @param cause error throwable
     */
    @Override
    public void recover(Message message, Throwable cause) {

        try {
            MessagePlayer messagePlayer = new ObjectMapper().readValue(message.getBody(),MessagePlayer.class);

            messageProducer.addToErrorQueue(MatchResultMessage
                    .builder()
                    .responseCode(ResponseCode.GENERIC_ERROR)
                    .responseMessage(cause.getMessage())
                    .inputPlayer(messagePlayer.getPlayerId())
                    .gameName(messagePlayer.getGameName())
                    .build());

        } catch (IOException e) {
            log.error("Failed to add failed message {} to the queue for {}", message, e.getMessage());
        }

        super.recover(message, cause);
    }
}
