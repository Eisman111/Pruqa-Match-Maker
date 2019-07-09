package com.pruqa.matchmakersettings.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableResourceServer
public class Oauth2ResourceConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .permitAll()
                .antMatchers("/webjars/**",
                        "/swagger-ui.html",
                        "/swagger",
                        "/swagger-resources/**",
                        "/v2/api-docs",
                        "/actuator/**")
                .permitAll()
                .antMatchers("/api/v1/internal/**")
                .hasIpAddress("127.0.0.1")
                .anyRequest()
                .access("hasRole ('USER')");
    }
}