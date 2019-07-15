package com.pruqa.matchmakerpreparer.messanger;

import com.pruqa.matchmakerlibrary.model.MatchResultMessage;

public interface PlayerProducer {

    /**
     * Add an error message to the queue for further management
     * @param matchResultMessage MatchResultMessage object
     */
    void addToErrorQueue(final MatchResultMessage matchResultMessage);
}
