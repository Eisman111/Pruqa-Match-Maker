package com.pruqa.matchmakercaller.messanger;

import com.pruqa.matchmakerlibrary.model.FailureMatchMessage;

public interface FailureMatchConsumer {

    void readMessage(final FailureMatchMessage messagePlayer);
}
