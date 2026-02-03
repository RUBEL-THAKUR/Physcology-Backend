package com.healthcare.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TherapistLoginResponseDTO {
    private String token;
    private TherapistResponseDTO therapist;
}
