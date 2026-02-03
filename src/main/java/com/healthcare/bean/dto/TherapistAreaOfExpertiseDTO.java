package com.healthcare.bean.dto;

import lombok.Data;

import java.util.List;

@Data
public class TherapistAreaOfExpertiseDTO {

    // Multiple expertise areas selected by therapist
    private List<String> expertiseAreas;
}
