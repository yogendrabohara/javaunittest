package com.yogi.springrestapimockito.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/**") // Apply to all routes
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users/**").permitAll() // Allow unauthenticated access
                        .anyRequest().authenticated() // Secure all other routes
                )
                .csrf(csrf -> csrf.disable()) // Modern way to disable CSRF
                .httpBasic(httpBasic -> {}); // or .formLogin()

        return http.build();
    }
}
