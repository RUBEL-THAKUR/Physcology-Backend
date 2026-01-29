package com.healthcare.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TherapistSignupRequest {
    private String firstName;
    private String lastName;
    private String emailId;
    private String password;
    private String mobileNumber;
    private boolean isAbove18;
    private boolean acceptedTerms;
}