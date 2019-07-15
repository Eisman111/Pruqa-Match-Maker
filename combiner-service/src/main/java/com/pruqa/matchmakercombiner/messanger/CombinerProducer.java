package com.pruqa.matchmakercombiner.messanger;

import com.pruqa.matchmakerlibrary.model.MatchResultMessage;

public interface CombinerProducer {

    /**
     * Add the match result to the result queue, good and bad results
     * @param matchResultMessage MatchResultMessage
     */
    void addToResultQueue(final MatchResultMessage matchResultMessage);
}
