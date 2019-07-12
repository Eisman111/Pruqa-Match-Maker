package com.pruqa.matchmakercaller.service;

import com.pruqa.matchmakercaller.generated.settings.invoker.ApiException;
import com.pruqa.matchmakerlibrary.model.MatchResultMessage;

public interface IResultMatchService {

    void notifyResultFlow(MatchResultMessage message) throws ApiException;
}
