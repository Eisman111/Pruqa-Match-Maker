package com.pruqa.matchmakersettings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MatchMakerSettingsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MatchMakerSettingsApplication.class, args);
    }

}
