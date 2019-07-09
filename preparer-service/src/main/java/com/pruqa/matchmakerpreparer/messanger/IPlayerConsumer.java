package com.pruqa.matchmakerpreparer.messanger;

import com.pruqa.matchmakerpreparer.model.MessagePlayer;

public interface IPlayerConsumer {

    void readMessage(final MessagePlayer messagePlayer);
}
