package com.licitaciones.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/users/test").permitAll()      // Público para testing
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/auth/test-auth0").permitAll()// Endpoints de auth públicos
                        .anyRequest().authenticated()                        // Todo lo demás requiere JWT
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())                     // Validación JWT de Auth0
                );

        return http.build();
    }
}