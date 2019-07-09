package com.pruqa.matchmakerpreparer.messanger;

import com.pruqa.matchmakerlibrary.model.FailureMessage;
import com.pruqa.matchmakerlibrary.model.ResponseCode;
import com.pruqa.matchmakerpreparer.model.MessagePlayer;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;

public class FailureQueueMessageRecoverer extends RejectAndDontRequeueRecoverer {

    private PlayerProducer messageProducer;

    public FailureQueueMessageRecoverer(final PlayerProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @Override
    public void recover(Message message, Throwable cause) {
        messageProducer.addToErrorQueue(FailureMessage
                        .builder()
                        .failureCode(ResponseCode.GENERIC_ERROR)
                        .failureMessage(cause.getMessage())
                        .playerId(((MessagePlayer) message).getPlayerId())
                        .gameName(((MessagePlayer) message).getGameName())
                        .build());
        super.recover(message, cause);
    }
}
