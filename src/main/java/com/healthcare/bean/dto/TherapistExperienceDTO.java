package com.healthcare.bean.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class TherapistExperienceDTO {

    private UUID therapistId;
    private String designation;
    private String organizationName;
    private LocalDate employmentFrom;
    private LocalDate employmentTo;
    private Boolean continuing;
}