package com.pruqa.matchmakerpreparer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MatchMakerPreparerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MatchMakerPreparerApplication.class, args);
    }

}
