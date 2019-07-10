package com.pruqa.matchmakercaller.messanger;

import com.pruqa.matchmakerlibrary.model.SuccessMatchMessage;

public interface FailureMatchConsumer {

    void readMessage(final SuccessMatchMessage messagePlayer);
}
