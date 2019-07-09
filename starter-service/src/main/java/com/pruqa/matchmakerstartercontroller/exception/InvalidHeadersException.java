package com.pruqa.matchmakerstartercontroller.exception;


import com.pruqa.matchmakerstartercontroller.utils.ResponseCode;

public class InvalidHeadersException extends StarterApiException {

    @Override
    public ResponseCode getResponseCode() {
        return ResponseCode.INVALID_HEADERS;
    }
}
