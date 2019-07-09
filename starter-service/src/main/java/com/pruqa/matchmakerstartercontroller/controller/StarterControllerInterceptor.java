package com.pruqa.matchmakerstartercontroller.controller;

import com.pruqa.matchmakerstartercontroller.exception.InvalidHeadersException;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class StarterControllerInterceptor implements HandlerInterceptor {

    public static final String SETTINGS_API = "/starter/api/";

    /**
     * Validate the headers before actually handling the request in the rest controller
     *
     * @param request httpRequest
     * @param response httpResponse
     * @param handler object
     * @return true if OK, Exception if KO
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if (request.getRequestURI().startsWith(SETTINGS_API)) {
            validateRequestHeader(request, "requestId");
            validateRequestHeader(request, "sessionId");
        }

        return true;
    }

    /**
     * Given an input HttpServletRequest and a header name throw an exception if blank
     * @param request httpServletRequest
     * @param header name
     */
    private void validateRequestHeader(HttpServletRequest request, String header) {
        if (StringUtils.isBlank(request.getHeader(header))) {
            log.error("Someone tried to contact the services with the missing header {}", header);
            throw new InvalidHeadersException();
        }
    }
}
