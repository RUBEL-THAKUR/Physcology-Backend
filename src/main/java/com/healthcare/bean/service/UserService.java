package com.healthcare.bean.service;


import com.healthcare.bean.dto.LoginRequest;
import com.healthcare.bean.dto.LoginResponse;
import com.healthcare.bean.dto.SignupUserRequest;
import com.healthcare.bean.model.AppUser;
import com.healthcare.bean.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {


    //    -----------------------    Password Encoding Logic
    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public AppUser signupUser(SignupUserRequest request) {
        AppUser user = new AppUser();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setEmailId(request.getEmailId());

        // Encrypt password here
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setIsActive(true);
        user.setIsDelete(false);

        return userRepository.save(user);
    }


//   --------------------  Login User here


    public LoginResponse loginUser(LoginRequest loginRequest) {
        AppUser user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return new LoginResponse("Login successful");
    }


}
