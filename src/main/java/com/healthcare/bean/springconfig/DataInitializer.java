package com.healthcare.bean.springconfig;

import com.healthcare.bean.model.Admin;
import com.healthcare.bean.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    @Bean
    CommandLineRunner initAdminUser(
            AdminRepository adminRepo,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            String adminEmail = "admin@healthcare.com";

            if (adminRepo.findByEmail(adminEmail).isEmpty()) {
                Admin admin = Admin.builder()
                        .email(adminEmail)
                        .password(passwordEncoder.encode("Admin@123"))
                        .build();

                adminRepo.save(admin);
                System.out.println("✅ Admin created successfully!");
                System.out.println("📧 Email: " + adminEmail);
                System.out.println("🔑 Password: Admin@123");
            } else {
                System.out.println("ℹ️ Admin already exists");
            }
        };
    }
}