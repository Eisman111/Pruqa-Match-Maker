package com.pruqa.matchmakerpreparer.messanger;

import com.pruqa.matchmakerlibrary.model.FailureMessage;
import com.pruqa.matchmakerpreparer.model.MessagePlayer;

public interface PlayerProducer {

    void addToErrorQueue(final FailureMessage failureMessage);
}
