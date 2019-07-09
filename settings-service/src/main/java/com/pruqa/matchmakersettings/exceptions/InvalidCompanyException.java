package com.pruqa.matchmakersettings.exceptions;

import com.pruqa.matchmakersettings.utils.ResponseCode;

public class InvalidCompanyException extends SettingsApiException {

    /**
     * Recover the response code of the exception
     *
     * @return response code
     */
    @Override
    public ResponseCode getResponseCode() {
        return ResponseCode.INVALID_COMPANY;
    }
}
