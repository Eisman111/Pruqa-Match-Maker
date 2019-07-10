package com.pruqa.matchmakercaller.messanger;

import com.pruqa.matchmakerlibrary.model.SuccessMatchMessage;

public interface SuccessMatchConsumer {

    void readMessage(final SuccessMatchMessage messagePlayer);
}
