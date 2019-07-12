package com.pruqa.matchmakercaller.service;

import com.pruqa.matchmakercaller.generated.caller.CompanyControllerApi;
import com.pruqa.matchmakercaller.generated.settings.invoker.ApiException;
import com.pruqa.matchmakercaller.generated.settings.model.CompanyApiResponse;
import com.pruqa.matchmakerlibrary.model.MatchResultMessage;

public abstract class ResultMatchService implements IResultMatchService {

    @Override
    public void notifyResultFlow(MatchResultMessage message) throws ApiException {

        CompanyApiResponse companyApiResponse = fetchCompanyApi(message.getGameName());

        CompanyControllerApi serverApi = getApiForCompany(companyApiResponse);

        notifyResultMatch(message, serverApi);
    }

    abstract CompanyApiResponse fetchCompanyApi(String gameName) throws ApiException;

    abstract CompanyControllerApi getApiForCompany(CompanyApiResponse companyApiResponse);

    abstract void notifyResultMatch(MatchResultMessage matchMessage, CompanyControllerApi serverApi);
}
