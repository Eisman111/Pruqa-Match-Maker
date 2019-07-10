package com.pruqa.matchmakercaller.service;

import com.pruqa.matchmakercaller.generated.invoker.ApiException;
import com.pruqa.matchmakercaller.generated.model.CompanyApiResponse;
import com.pruqa.matchmakerlibrary.model.ResultMessage;

public abstract class ResultMatchService implements IResultMatchService {

    @Override
    public void notifyResultFlow(ResultMessage message) throws ApiException {

        CompanyApiResponse companyApiResponse = fetchCompanyApi(message.getGameName());

        notifyResultMatch(message,companyApiResponse);
    }

    abstract CompanyApiResponse fetchCompanyApi(String gameName) throws ApiException;

    abstract void notifyResultMatch(ResultMessage matchMessage, CompanyApiResponse companyApiResponse);
}
