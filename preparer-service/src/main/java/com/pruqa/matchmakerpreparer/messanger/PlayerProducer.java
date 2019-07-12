package com.pruqa.matchmakerpreparer.messanger;

import com.pruqa.matchmakerlibrary.model.MatchResultMessage;

public interface PlayerProducer {

    void addToErrorQueue(final MatchResultMessage matchResultMessage);
}
