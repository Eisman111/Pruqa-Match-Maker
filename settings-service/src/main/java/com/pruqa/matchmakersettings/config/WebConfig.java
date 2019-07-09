package com.pruqa.matchmakersettings.config;

import com.pruqa.matchmakersettings.controller.SettingsControllerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private SettingsControllerInterceptor settingsControllerInterceptor;

    @Autowired
    WebConfig (SettingsControllerInterceptor settingsControllerInterceptor) {
        this.settingsControllerInterceptor = settingsControllerInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(settingsControllerInterceptor);
    }
}
