package com.healthcare.bean.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;

//    It For email Also

    private String emailId; //  Optional for OTP login
    private String mobileNumber; // Optional for OTP login
    private  String otp;   // OTP value





}
