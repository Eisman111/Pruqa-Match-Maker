package com.pruqa.matchmakercombiner.messanger;

import com.pruqa.matchmakerlibrary.model.SuccessMatchMessage;
import com.pruqa.matchmakerlibrary.model.FailureMatchMessage;

public interface CombinerProducer {

    void addToErrorQueue(final FailureMatchMessage failureMatchMessage);

    void addToSuccessQueue(final SuccessMatchMessage successMatchMessage);
}
