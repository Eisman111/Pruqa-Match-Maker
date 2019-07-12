package com.pruqa.matchmakercombiner.messanger;

import com.pruqa.matchmakerlibrary.model.MatchResultMessage;

public interface CombinerProducer {

    void addToResultQueue(final MatchResultMessage matchResultMessage);
}
