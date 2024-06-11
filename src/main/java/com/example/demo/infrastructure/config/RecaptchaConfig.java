package com.example.demo.infrastructure.config;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("google.recaptcha")
public class RecaptchaConfig {

    @Setter
    @Getter
    private Map<String, String> secrets = new HashMap<>();

    public String getSecret(String clientType) {
        return secrets.get(clientType);
    }

}
