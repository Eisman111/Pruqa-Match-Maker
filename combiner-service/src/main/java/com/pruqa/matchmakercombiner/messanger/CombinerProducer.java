package com.pruqa.matchmakercombiner.messanger;

import com.pruqa.matchmakercombiner.model.MatchedPlayerMessage;
import com.pruqa.matchmakerlibrary.model.FailureMessage;

public interface CombinerProducer {

    void addToErrorQueue(final FailureMessage failureMessage);

    void addToSuccessQueue(final MatchedPlayerMessage matchedPlayerMessage);
}
