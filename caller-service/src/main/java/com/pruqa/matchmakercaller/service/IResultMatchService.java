package com.pruqa.matchmakercaller.service;

import com.pruqa.matchmakercaller.generated.settings.invoker.ApiException;
import com.pruqa.matchmakerlibrary.model.MatchResultMessage;

public interface IResultMatchService {

    /**
     * Notify the client of the match result
     * @param message MatchResultMessage
     * @throws ApiException
     */
    void notifyResultFlow(MatchResultMessage message) throws ApiException;
}
