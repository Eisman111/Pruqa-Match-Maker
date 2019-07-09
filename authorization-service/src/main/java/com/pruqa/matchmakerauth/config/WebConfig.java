package com.pruqa.matchmakerauth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebConfig {

    // == Fields ==
    @Value("${app.textencryptor.password}")
    private String password;
    @Value("${app.textencryptor.salt}")
    private String salt;

    // == public beans ==
    // Bean necessary to launch security encoding
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // Bean necessary to manage the configuration of the encryption protocol
    @Bean
    public TextEncryptor eTextEncryptor () {
        return Encryptors.delux(password,salt);
    }
}
