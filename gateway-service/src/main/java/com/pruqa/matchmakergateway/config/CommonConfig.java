package com.pruqa.matchmakergateway.config;

import com.netflix.zuul.ZuulFilter;
import com.pruqa.matchmakergateway.filter.SecurityZuulFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {

    @Bean
    public SecurityZuulFilter securityZuulFilter() {
        return new SecurityZuulFilter();
    }
}
