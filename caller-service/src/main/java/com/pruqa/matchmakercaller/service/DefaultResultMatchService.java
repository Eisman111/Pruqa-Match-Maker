package com.pruqa.matchmakercaller.service;

import com.pruqa.matchmakercaller.generated.settings.SettingsControllerApi;
import com.pruqa.matchmakercaller.generated.settings.invoker.ApiException;
import com.pruqa.matchmakercaller.generated.settings.model.CompanyApiRequest;
import com.pruqa.matchmakercaller.generated.settings.model.CompanyApiResponse;
import com.pruqa.matchmakerlibrary.model.MatchResultMessage;
import com.pruqa.matchmakerlibrary.model.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DefaultResultMatchService extends ResultMatchService {

    private SettingsControllerApi settingsControllerApi;

    @Autowired
    public DefaultResultMatchService(SettingsControllerApi settingsControllerApi) {
        this.settingsControllerApi = settingsControllerApi;
    }

    @Override
    CompanyApiResponse fetchCompanyApi(String gameName) throws ApiException {
        CompanyApiRequest request = new CompanyApiRequest();
        request.setGameName(gameName);
        return settingsControllerApi.getCompanyApiSettingsUsingPOST("requestId","sessionId", request);
    }

    @Override
    void notifyResultMatch(MatchResultMessage resultMessage, CompanyApiResponse companyApiResponse) {
        if (resultMessage.getResponseCode().equals(ResponseCode.MATCHED)) {
            log.info("SUCCESS!!! message {}, response {}", resultMessage.toString(), companyApiResponse.toString());
        } else {
            log.info("FAILURE!!! message {}, response {}", resultMessage.toString(), companyApiResponse.toString());
        }
    }
}
