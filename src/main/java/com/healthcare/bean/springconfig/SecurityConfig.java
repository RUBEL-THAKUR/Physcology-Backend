package com.healthcare.bean.springconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;




@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Disable CSRF for POST from Postman
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/signup", "/api/users/login").permitAll() // Allow both endpoints.antMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/api/users/request-otp", "/api/users/verify-otp").permitAll() // Allow otp request & verify
                        .requestMatchers("/api/psychologists/**").permitAll() // Allow otp request & verify
                        .requestMatchers("/api/appointments/**").permitAll() // Allow otp request & verify

                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        .anyRequest().authenticated()           // 🔒 Secure other endpoints
                ).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}



//              SpringbootApp
// password:-   dtjd haql gddx takr