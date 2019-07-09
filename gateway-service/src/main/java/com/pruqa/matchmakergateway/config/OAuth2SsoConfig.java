package com.pruqa.matchmakergateway.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableOAuth2Sso
@Configuration
public class OAuth2SsoConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("security/oauth/token", "security/oauth/authorize**", "/publishes", "security/register")
                .permitAll()
                .and()
                .requestMatchers()
                .antMatchers("starter/**")
                .and()
                .authorizeRequests()
                .antMatchers("starter/**")
                .access("hasRole ('USER')");
    }
}
