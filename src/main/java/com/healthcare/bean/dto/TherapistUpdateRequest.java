package com.healthcare.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TherapistUpdateRequest {
    private String firstName;
    private String lastName;
    private String mobileNumber;
}