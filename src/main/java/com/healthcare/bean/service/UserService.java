package com.healthcare.bean.service;

import com.healthcare.bean.dto.LoginRequest;
import com.healthcare.bean.dto.LoginResponse;
import com.healthcare.bean.dto.SignupUserRequest;
import com.healthcare.bean.model.AppUser;
import com.healthcare.bean.repository.UserRepository;
import com.healthcare.bean.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;   // ✅ final + constructor injected

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // ---------------- SIGNUP ----------------
    public AppUser signupUser(SignupUserRequest request) {
        AppUser user = new AppUser();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setEmailId(request.getEmailId());

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setIsActive(true);
        user.setIsDelete(false);

        return userRepository.save(user);
    }

    // ---------------- LOGIN ----------------
    public LoginResponse loginUser(LoginRequest loginRequest) {

        AppUser user = userRepository
                .findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // 🔐 Generate JWT
        String token = jwtUtil.generateToken(user.getUsername());

        return new LoginResponse(
                "Login successful",
                token,
                user.getUserId(),
                user.getUsername()
        );
    }
}
