package com.pruqa.matchmakerpreparer.messanger;

import com.pruqa.matchmakerlibrary.model.FailureMatchMessage;

public interface PlayerProducer {

    void addToErrorQueue(final FailureMatchMessage failureMatchMessage);
}
