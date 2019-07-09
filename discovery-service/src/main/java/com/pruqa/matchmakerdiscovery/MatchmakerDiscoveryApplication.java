package com.pruqa.matchmakerdiscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MatchmakerDiscoveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(MatchmakerDiscoveryApplication.class, args);
    }

}
