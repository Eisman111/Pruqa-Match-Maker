package com.pruqa.matchmakerpreparer.messanger;

import com.pruqa.matchmakerpreparer.model.MessagePlayer;

public interface PlayerConsumer {

    /**
     * Read the input player message from a Queue
     * @param messagePlayer MessagePlayer object
     */
    void readMessage(final MessagePlayer messagePlayer);
}
