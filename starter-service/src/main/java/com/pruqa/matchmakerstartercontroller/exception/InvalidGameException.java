package com.pruqa.matchmakerstartercontroller.exception;


import com.pruqa.matchmakerstartercontroller.utils.ResponseCode;

public class InvalidGameException extends StarterApiException {

    /**
     * Recover the response code of the exception
     *
     * @return response code
     */
    @Override
    public ResponseCode getResponseCode() {
        return ResponseCode.INVALID_GAME;
    }
}
