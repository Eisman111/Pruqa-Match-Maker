package com.pruqa.matchmakercaller.messanger;

import com.pruqa.matchmakerlibrary.model.MatchResultMessage;

public interface MatchResultConsumer {

    void readMessage(final MatchResultMessage messagePlayer);
}
