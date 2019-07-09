package com.pruqa.matchmakerpreparer.messanger;

import com.pruqa.matchmakerpreparer.model.MessagePlayer;

public interface IPlayerProducer {

    void addToErrorQueue(final MessagePlayer messagePlayer);
}
