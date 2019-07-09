package com.pruqa.matchmakerpreparer.messanger;

import com.pruqa.matchmakerpreparer.model.MessagePlayer;

public interface PlayerConsumer {

    void readMessage(final MessagePlayer messagePlayer);
}
