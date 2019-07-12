package com.pruqa.matchmakercaller.service;

import com.pruqa.matchmakercaller.generated.caller.CompanyControllerApi;
import com.pruqa.matchmakercaller.generated.caller.invoker.ApiClient;
import com.pruqa.matchmakercaller.generated.caller.model.MatchMakerResult;
import com.pruqa.matchmakercaller.generated.settings.SettingsControllerApi;
import com.pruqa.matchmakercaller.generated.settings.invoker.ApiException;
import com.pruqa.matchmakercaller.generated.settings.model.CompanyApiRequest;
import com.pruqa.matchmakercaller.generated.settings.model.CompanyApiResponse;
import com.pruqa.matchmakerlibrary.model.MatchResultMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DefaultResultMatchService extends ResultMatchService {

    private SettingsControllerApi settingsControllerApi;

    @Autowired
    public DefaultResultMatchService(final SettingsControllerApi settingsControllerApi) {
        this.settingsControllerApi = settingsControllerApi;
    }

    @Override
    CompanyApiResponse fetchCompanyApi(String gameName) throws ApiException {
        CompanyApiRequest request = new CompanyApiRequest();
        request.setGameName(gameName);
        return settingsControllerApi.getCompanyApiSettingsUsingPOST("requestId","sessionId", request);
    }

    @Override
    CompanyControllerApi getApiForCompany(CompanyApiResponse companyApiResponse) {
        CompanyControllerApi companyControllerApi = new CompanyControllerApi();
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(companyApiResponse.getResponseEndpoint());
        companyControllerApi.setApiClient(apiClient);
        return companyControllerApi;
    }

    @Override
    void notifyResultMatch(MatchResultMessage resultMessage, CompanyControllerApi companyControllerApi) {
        MatchMakerResult result = new MatchMakerResult();

        result.setGameName(resultMessage.getGameName());
        result.setInputPlayer(resultMessage.getInputPlayer());

       switch (resultMessage.getResponseCode()) {
           case MATCHED:
               result.setResponseCode(MatchMakerResult.ResponseCodeEnum.MATCHED);
               result.setMatchedPlayer(resultMessage.getMatchedPlayer());
               break;
           case NO_MATCHING_PLAYER:
               result.setResponseCode(MatchMakerResult.ResponseCodeEnum.NO_MATCHING_PLAYERS);
               result.setResponseMessage(resultMessage.getResponseMessage());
               break;
           case GENERIC_ERROR:
               default:
                   result.setResponseCode(MatchMakerResult.ResponseCodeEnum.GENERIC_ERROR);
                   result.setResponseMessage(resultMessage.getResponseMessage());
                   break;
       }

        try {
            companyControllerApi.getMatchMakingResultUsingPOST("requestId","sessionId",result);
        } catch (com.pruqa.matchmakercaller.generated.caller.invoker.ApiException e) {
            e.printStackTrace();
        }
    }
}
