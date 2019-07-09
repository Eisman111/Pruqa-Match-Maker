package com.pruqa.matchmakerstartercontroller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MatchMakerStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(MatchMakerStarterApplication.class, args);
    }

}
