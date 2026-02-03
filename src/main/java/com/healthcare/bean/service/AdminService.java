package com.healthcare.bean.service;

import com.healthcare.bean.dto.AdminLoginRequest;
import com.healthcare.bean.model.Admin;
import com.healthcare.bean.repository.AdminRepository;
import com.healthcare.bean.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public Map<String, Object> login(AdminLoginRequest request) {

        Admin admin = adminRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid admin credentials"));

        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new RuntimeException("Invalid admin credentials");
        }

        String token = jwtUtil.generateAdminToken(admin.getId(), admin.getEmail());

        return Map.of(
                "token", token,
                "role", "ADMIN"
        );
    }
}
