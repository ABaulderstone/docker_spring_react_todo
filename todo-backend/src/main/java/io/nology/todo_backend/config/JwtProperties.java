package io.nology.todo_backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import jakarta.annotation.PostConstruct;

@Configuration
public class JwtProperties {
    @Autowired
    private Environment env;
    private String secretKey;
    private long expirationTime;

    @PostConstruct
    private void init() {
        this.secretKey = env.getProperty("JWT_SECRET_KEY");
        this.expirationTime = Long.parseLong(env.getProperty("JWT_EXPIRY"));
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }
}
