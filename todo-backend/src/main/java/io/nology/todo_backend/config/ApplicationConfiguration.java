package io.nology.todo_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.nology.todo_backend.auth.CustomUserDetails;
import io.nology.todo_backend.auth.CustomUserDetailsService;
import io.nology.todo_backend.user.UserRepository;

@Configuration
public class ApplicationConfiguration {
    private final CustomUserDetailsService customUserDetailsService;

    public ApplicationConfiguration(CustomUserDetailsService customUserDetailsService, UserRepository repo) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    UserDetailsService userDetailsService() {
        return this.customUserDetailsService;
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
