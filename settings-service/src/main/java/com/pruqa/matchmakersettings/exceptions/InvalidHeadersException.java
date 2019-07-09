package com.pruqa.matchmakersettings.exceptions;

import com.pruqa.matchmakersettings.utils.ResponseCode;

public class InvalidHeadersException extends SettingsApiException {

    @Override
    public ResponseCode getResponseCode() {
        return ResponseCode.INVALID_HEADERS;
    }
}
