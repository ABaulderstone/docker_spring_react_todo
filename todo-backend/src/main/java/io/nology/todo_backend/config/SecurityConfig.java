package io.nology.todo_backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import io.nology.todo_backend.auth.JwtAuthFilter;
import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationProvider authProvider;
    private final CorsConfigurationSource corsConfigSource;
    private final JwtAuthFilter jwtFilter;

    @Autowired
    public SecurityConfig(AuthenticationProvider authProvider, CorsConfigurationSource corsConfigSource,
            JwtAuthFilter authFilter) {
        this.authProvider = authProvider;
        this.corsConfigSource = corsConfigSource;
        this.jwtFilter = authFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(CsrfConfigurer::disable)
                .cors(c -> c.configurationSource(corsConfigSource))
                .authorizeHttpRequests(
                        a -> a.requestMatchers("/auth/*").permitAll().dispatcherTypeMatchers(DispatcherType.ERROR)
                                .permitAll()
                                .anyRequest().authenticated())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(
                        eh -> eh.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                                .accessDeniedHandler(new CustomAccessDeniedHandler()))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
