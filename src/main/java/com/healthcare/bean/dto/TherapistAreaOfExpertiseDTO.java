package com.healthcare.bean.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class TherapistAreaOfExpertiseDTO {


    private UUID therapistId;

    private List<String> expertiseAreas; // Multiple areas at once
}