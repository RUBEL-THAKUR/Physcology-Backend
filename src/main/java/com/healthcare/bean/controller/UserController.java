package com.healthcare.bean.controller;

import com.healthcare.bean.dto.LoginRequest;
import com.healthcare.bean.dto.LoginResponse;
import com.healthcare.bean.dto.SignupUserRequest;
import com.healthcare.bean.model.AppUser;
import com.healthcare.bean.service.OtpService;
import com.healthcare.bean.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OtpService otpService;

    @PostMapping("/signup")
    public ResponseEntity<AppUser> signupUser(@Valid @RequestBody SignupUserRequest r) {
        return ResponseEntity.ok(userService.signupUser(r));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest r) {
        return ResponseEntity.ok(userService.loginUser(r));
    }

    @PostMapping("/request-otp")
    public ResponseEntity<String> requestOtp(@RequestBody LoginRequest request){
        String identifier = request.getEmailId() != null && !request.getEmailId().isBlank()
                ? request.getEmailId()
                : request.getMobileNumber();

        if (identifier == null || identifier.isBlank()) {
            return ResponseEntity.badRequest().body("Email or mobile required");
        }

        otpService.generateAndSendOtp(identifier);  // this handles both email and mobile

        return ResponseEntity.ok("OTP sent to " + identifier);
    }


    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody LoginRequest r) {
        String id = r.getEmailId() != null ? r.getEmailId() : r.getMobileNumber();
        if (id == null || r.getOtp() == null) return ResponseEntity.badRequest().body("Identifier and OTP required");
        return otpService.verifyOtp(id, r.getOtp())
                ? ResponseEntity.ok("OTP verified successfully")
                : ResponseEntity.status(401).body("Invalid or expired OTP");
    }

}
