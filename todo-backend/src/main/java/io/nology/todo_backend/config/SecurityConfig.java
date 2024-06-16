package io.nology.todo_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationProvider authProvider;

    public SecurityConfig(AuthenticationProvider authProvider) {
        this.authProvider = authProvider;
    }

}
