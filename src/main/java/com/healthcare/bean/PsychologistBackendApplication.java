package com.healthcare.bean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})  // ✅ Add this
public class PsychologistBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(PsychologistBackendApplication.class, args);
    }
}