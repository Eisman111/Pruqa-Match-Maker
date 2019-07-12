package com.pruqa.matchmakercaller.config;

import com.pruqa.matchmakercaller.generated.caller.CompanyControllerApi;
import com.pruqa.matchmakercaller.generated.settings.SettingsControllerApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CallerConfig {

    /**
     * Access to the Settings Service API
     * @return settingsControllerApi
     */
    @Bean
    public SettingsControllerApi settingsApi() {
        return new SettingsControllerApi();
    }
}
