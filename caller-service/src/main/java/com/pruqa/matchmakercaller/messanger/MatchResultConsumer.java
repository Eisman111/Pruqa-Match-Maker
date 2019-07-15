package com.pruqa.matchmakercaller.messanger;

import com.pruqa.matchmakerlibrary.model.MatchResultMessage;

public interface MatchResultConsumer {

    /**
     * On a fixed rate read a message from the result queue
     * @param messagePlayer MatchResultMessage
     */
    void readMessage(final MatchResultMessage messagePlayer);
}
