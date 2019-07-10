package com.pruqa.matchmakercaller.service;

import com.pruqa.matchmakercaller.generated.invoker.ApiException;
import com.pruqa.matchmakerlibrary.model.ResultMessage;
import com.pruqa.matchmakerlibrary.model.SuccessMatchMessage;

public interface IResultMatchService {

    void notifyResultFlow(ResultMessage message) throws ApiException;
}
