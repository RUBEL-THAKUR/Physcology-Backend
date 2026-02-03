package com.healthcare.bean.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TherapistExperienceDTO {

    private String designation;
    private String organizationName;
    private LocalDate employmentFrom;
    private LocalDate employmentTo;
    private Boolean continuing;
}
