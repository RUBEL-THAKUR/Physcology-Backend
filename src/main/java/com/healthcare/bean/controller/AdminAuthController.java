package com.healthcare.bean.controller;

import com.healthcare.bean.dto.AdminLoginRequest;
import com.healthcare.bean.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/auth")
@RequiredArgsConstructor
public class AdminAuthController {

    private final AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AdminLoginRequest request) {
        System.out.println("===========================================");
        System.out.println("✅ ✅ ✅ CONTROLLER REACHED! ✅ ✅ ✅");
        System.out.println("📧 Email: " + request.getEmail());
        System.out.println("===========================================");

        try {
            Map<String, Object> response = adminService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            System.err.println("❌ Login failed: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}