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

    /**
     * Fetch the company details from the SettingsApi searching by game name
     * @param gameName String
     * @return CompanyApiResponse
     * @throws ApiException
     */
    abstract CompanyApiResponse fetchCompanyApi(String gameName) throws ApiException;

    /**
     * Build the Api for contacting the client company endpoint
     * @param companyApiResponse CompanyApiResponse
     * @return CompanyControllerApi
     */
    abstract CompanyControllerApi getApiForCompany(CompanyApiResponse companyApiResponse);

    /**
     * Notify the client of the result
     * @param matchMessage MatchResultMessage
     * @param serverApi CompanyControllerApi
     */
    abstract void notifyResultMatch(MatchResultMessage matchMessage, CompanyControllerApi serverApi);
}
