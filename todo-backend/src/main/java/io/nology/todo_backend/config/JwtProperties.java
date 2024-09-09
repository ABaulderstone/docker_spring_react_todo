package io.nology.todo_backend.config;

import org.springframework.context.annotation.Configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String secretKey;
    private final long expirationTime = 86400000; // 24 hours in milliseconds

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public long getExpirationTime() {
        return expirationTime;
    }
}
