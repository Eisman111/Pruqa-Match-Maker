package com.pruqa.matchmakercaller.service;

import com.pruqa.matchmakercaller.generated.SettingsControllerApi;
import com.pruqa.matchmakercaller.generated.invoker.ApiException;
import com.pruqa.matchmakercaller.generated.model.CompanyApiRequest;
import com.pruqa.matchmakercaller.generated.model.CompanyApiResponse;
import com.pruqa.matchmakerlibrary.model.FailureMatchMessage;
import com.pruqa.matchmakerlibrary.model.ResultMessage;
import com.pruqa.matchmakerlibrary.model.SuccessMatchMessage;
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
    void notifyResultMatch(ResultMessage resultMessage, CompanyApiResponse companyApiResponse) {
        if (resultMessage instanceof SuccessMatchMessage) {
            log.info("SUCCESS!!! message {}, response {}", resultMessage.toString(), companyApiResponse.toString());
        } else if (resultMessage instanceof FailureMatchMessage) {
            log.info("FAILURE!!! message {}, response {}", resultMessage.toString(), companyApiResponse.toString());
        } else {
            log.error("The input result message is not of any type valid. {}", resultMessage.getClass().getCanonicalName());
        }
    }
}
